package com.pae.server.chat.service;

import com.pae.server.board.repository.goods.GoodsBoardRepository;
import com.pae.server.chat.domain.ChatMessage;
import com.pae.server.chat.domain.ChatRoom;
import com.pae.server.chat.dto.request.ChatSendReqDto;
import com.pae.server.chat.dto.response.ChatMessageRespDto;
import com.pae.server.chat.dto.response.ChatRoomRespDto;
import com.pae.server.chat.dto.response.ChatSendRespDto;
import com.pae.server.chat.repository.mongo.ChatMessageRepository;
import com.pae.server.chat.repository.ChatRoomRepository;
import com.pae.server.common.enums.CustomResponseStatus;
import com.pae.server.common.exception.CustomException;
import com.pae.server.member.domain.Member;
import com.pae.server.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatServiceImpl implements ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final MemberRepository memberRepository;
    private final GoodsBoardRepository goodsBoardRepository;

    @Override
    public ChatSendRespDto processMessage(ChatSendReqDto chatSendReqDto) {
        // 1. 채팅방이 존재하는지 확인
        ChatRoom chatRoom;
        if (chatSendReqDto.chatRoomId() == null) {
            // 첫 채팅이 시작된 채팅방 -> 채팅방을 생성해줘야함.
            chatRoom = createChatRoom(chatSendReqDto);
        } else {
            // 채팅방이 존재하는 경우
            chatRoom = validGoodsChatRoom(chatSendReqDto.initiatorId(), chatSendReqDto.recipientId(), chatSendReqDto.goodsBoardId());
        }

        // 2. 메시지 저장
        return ChatSendRespDto.from(saveChatMessage(chatSendReqDto, chatRoom.getId()));
    }

    @Override
    public List<ChatRoomRespDto> queryChatRooms(Long memberId) {
        // 조회를 한 클라이언트의 정보
        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new CustomException(CustomResponseStatus.MEMBER_NOT_FOUND)
        );
        Long trustMemberId = member.getId();

        // 속해있는 채팅방 싹다 가져오기
        List<ChatRoom> allChatRoom = chatRoomRepository.findAllByInitiatorIdOrRecipientId(trustMemberId, trustMemberId);

        List<ChatRoomRespDto> result = new ArrayList<>(allChatRoom.size());
        for (ChatRoom chatRoom : allChatRoom) {
            Long otherMemberId = Objects.equals(chatRoom.getInitiatorId(), trustMemberId) ? chatRoom.getRecipientId() : chatRoom.getInitiatorId();

            Member otherMember = memberRepository.findById(otherMemberId).orElseThrow(() ->
                    new CustomException(CustomResponseStatus.MEMBER_NOT_FOUND)
            );

            String goodsBoardThumbnailUrl = goodsBoardRepository.getGoodsBoardThumbnail(chatRoom.getGoodsBoardId());
            ChatMessage lastChatMessage = chatMessageRepository.findTopByChatRoomIdOrderByCreatedAtDesc(chatRoom.getId())
                    .orElseThrow(() -> new CustomException(CustomResponseStatus.LAST_CHAT_NOT_FOUND));

            LocalDateTime createdAt = lastChatMessage.getCreatedAt();
            long unreadMessageCount = chatMessageRepository.countByChatRoomIdAndSenderIdNotAndIsReadFalse(chatRoom.getId(), memberId);
            result.add(
                    ChatRoomRespDto.of(
                            chatRoom.getId(),
                            otherMember.getNickname(),
                            otherMember.getPhotoData().getPhotoUrl(),
                            lastChatMessage.getMessageContent(),
                            LocalDate.of(createdAt.getYear(), createdAt.getMonth(), createdAt.getDayOfMonth()),
                            goodsBoardThumbnailUrl,
                            unreadMessageCount
                    )
            );
        }
        return result;
    }

    @Override
    public List<ChatMessageRespDto> queryChatMessages(Long chatRoomId) {
        List<ChatMessage> chatMessages = chatMessageRepository.findAllByChatRoomIdOrderByCreatedAtAsc(chatRoomId);

        return chatMessages.stream().map(ChatMessageRespDto::from).toList();
    }

    private ChatMessage saveChatMessage(ChatSendReqDto chatSendReqDto, Long trustChatRoomId) {
        return chatMessageRepository.save(ChatMessage.of(chatSendReqDto, trustChatRoomId));
    }

    private ChatRoom validGoodsChatRoom(Long initiatorId, Long recipientId, Long goodsBoardId) {
        return chatRoomRepository.findByInitiatorIdAndRecipientIdAndGoodsBoardId(
                initiatorId, recipientId, goodsBoardId
        ).orElseThrow(() -> new CustomException(CustomResponseStatus.GOODS_CHAT_NOT_FOUND));
    }

    private ChatRoom createChatRoom(ChatSendReqDto chatSendReqDto) {
        return chatRoomRepository.save(ChatRoom.from(chatSendReqDto));
    }

}

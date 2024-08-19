package com.pae.server.chat.service;

import com.pae.server.board.repository.goods.GoodsBoardRepository;
import com.pae.server.chat.domain.ChatMessage;
import com.pae.server.chat.domain.ChatRoom;
import com.pae.server.chat.dto.request.ChatRoomCreateReqDto;
import com.pae.server.chat.dto.request.ChatSendReqDto;
import com.pae.server.chat.dto.response.ChatMessageRespDto;
import com.pae.server.chat.dto.response.ChatRoomCreateRespDto;
import com.pae.server.chat.dto.response.ChatRoomRespDto;
import com.pae.server.chat.dto.response.ChatSendRespDto;
import com.pae.server.chat.repository.mongo.ChatMessageRepository;
import com.pae.server.chat.repository.ChatRoomRepository;
import com.pae.server.common.enums.CustomResponseStatus;
import com.pae.server.common.exception.CustomException;
import com.pae.server.member.domain.Member;
import com.pae.server.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
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
@Transactional
public class ChatServiceImpl implements ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final MemberRepository memberRepository;
    private final GoodsBoardRepository goodsBoardRepository;

    @Override
    public ChatRoomCreateRespDto createChatRoom(ChatRoomCreateReqDto chatRoomCreateReqDto) {
        validGoodsChatRoom(chatRoomCreateReqDto.initiatorId(), chatRoomCreateReqDto.recipientId(), chatRoomCreateReqDto.goodsBoardId());

        ChatRoom savedChatRoom = chatRoomRepository.save(ChatRoom.from(chatRoomCreateReqDto));
        return ChatRoomCreateRespDto.from(savedChatRoom.getId());
    }

    @Override
    public ChatSendRespDto sendMessage(ChatSendReqDto chatSendReqDto, Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(
                () -> new CustomException(CustomResponseStatus.GOODS_CHAT_NOT_FOUND)
        );
        if (Boolean.FALSE.equals(chatRoom.getIsActivate())) chatRoom.activateChatRoom();

        return ChatSendRespDto.from(saveChatMessage(chatSendReqDto, chatRoomId));
    }

    @Override
    public List<ChatRoomRespDto> queryChatRooms(Long memberId) {
        // 조회를 한 클라이언트의 정보
        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new CustomException(CustomResponseStatus.MEMBER_NOT_FOUND)
        );
        Long trustMemberId = member.getId();

        // 속해있는 채팅방 싹다 가져오기
        List<ChatRoom> allChatRoom = chatRoomRepository.findAllByIsActivateTrueAndInitiatorIdOrRecipientId(trustMemberId, trustMemberId);

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

    private void validGoodsChatRoom(Long initiatorId, Long recipientId, Long goodsBoardId) {
        chatRoomRepository.findByInitiatorIdAndRecipientIdAndGoodsBoardId(
                initiatorId, recipientId, goodsBoardId
        ).ifPresent(chatRoom -> {
            throw new CustomException(CustomResponseStatus.EXIST_CHAT_ROOM);
        });
    }

}

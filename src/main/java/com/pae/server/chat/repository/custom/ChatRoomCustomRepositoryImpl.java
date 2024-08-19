package com.pae.server.chat.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.pae.server.chat.domain.QChatRoom.chatRoom;

@Repository
@RequiredArgsConstructor
public class ChatRoomCustomRepositoryImpl implements ChatRoomCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public long getChatRoomCountByGoodsBoard(Long goodsBoardId) {
        Long count = jpaQueryFactory
                .select(chatRoom.count())
                .from(chatRoom)
                .where(chatRoom.goodsBoardId.eq(goodsBoardId)
                        .and(chatRoom.isActivate.isTrue()))  // 활성화된 채팅방만 카운트
                .fetchOne();

        return count != null ? count : 0L;
    }

}

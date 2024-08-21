package com.pae.server.chat.repository.custom;

public interface ChatRoomCustomRepository {
    /***
     * 특정 거래게시글에 해당되는 채팅방 개수 조회
     */
    long getChatRoomCountByGoodsBoard(Long goodsBoardId);
}

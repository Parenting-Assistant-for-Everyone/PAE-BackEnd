package com.pae.server.board.dto.response;

import com.pae.server.board.domain.GoodsBoard;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record GoodsBoardSimpleInfoDto(
        Long boardId,
        String title,
        String daysAgo,
        String price,
        int likeNum,
        String saleStatus,
        String thumbnailUrl,
        int chattingNum,
        String address
) {
    public static GoodsBoardSimpleInfoDto of(GoodsBoard goodsBoard, Integer likeNum, String thumbnailUrl) {
        return GoodsBoardSimpleInfoDto.builder()
                .boardId(goodsBoard.getId())
                .title(goodsBoard.getTitle())
                .daysAgo(goodsBoard.getDaysAgo(LocalDateTime.now()))
                .price(goodsBoard.getPriceBySaleType())
                .saleStatus(goodsBoard.getSaleStatus().toString())
                .likeNum(likeNum)
                .thumbnailUrl(thumbnailUrl) // Todo : 채워넣어야함
//                .chattingNum(0) // Todo : 채워넣어야함
//                .address(null) // Todo : 채워넣어야함
                .build();
    }
}

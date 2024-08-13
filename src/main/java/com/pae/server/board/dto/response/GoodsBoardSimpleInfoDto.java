package com.pae.server.board.dto.response;

import com.pae.server.board.domain.GoodsBoard;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record GoodsBoardSimpleInfoDto(
        String title,
        String daysAgo,
        String price,
        int likeNum,
        String saleStatus
//        String thumbnailUrl
//        int chattingNum,
//        String address,
) {
    public static GoodsBoardSimpleInfoDto of(GoodsBoard goodsBoard, Integer likeNum) {
        return GoodsBoardSimpleInfoDto.builder()
                .title(goodsBoard.getTitle())
                .daysAgo(goodsBoard.getDaysAgo(LocalDateTime.now()))
                .price(goodsBoard.getPriceBySaleType())
                .saleStatus(goodsBoard.getSaleStatus().toString())
                .likeNum(likeNum)
                .build();
    }
}

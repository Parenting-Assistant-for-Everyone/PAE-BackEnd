package com.pae.server.board.dto.response;

import com.pae.server.board.domain.GoodsBoard;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record GoodsBoardDetailRespDto(
        String nickname,
//        String profileUrl,

        String title,
        String category,
        String address,
        String description,
        String price,
        LocalDateTime createdAt,

        List<String> goodsImgUrls,

        int chatCount,
        int likeCount
) {
    public static GoodsBoardDetailRespDto from(GoodsBoard goods) {
        return GoodsBoardDetailRespDto.builder()
                .nickname(goods.getMember().getNickname())
//                .profileUrl(goods.getMember())
                .title(goods.getTitle())
                .category(goods.getGoodsCategory().getKoreaName())
//                .address(goods.getAddress())
                .description(goods.getContent())
                .price(goods.getPriceBySaleType())
                .createdAt(goods.getCreatedAt())
//                .chatCount(goods.getChatCount())
                .likeCount(goods.getLikes().size())
                .build();
    }
}

package com.pae.server.board.dto.response;

import com.pae.server.board.domain.GoodsBoard;
import com.pae.server.image.dto.response.ImageData;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record GoodsBoardDetailRespDto(
        Long boardId,

        String nickname,
        String profileUrl,

        String title,
        String category,
        String address,
        String description,
        String price,
        LocalDateTime createdAt,
        Integer viewCount,

        List<ImageData> goodsImgData,

        int chatCount,
        int likeCount
) {
    public static GoodsBoardDetailRespDto from(GoodsBoard goods) {
        return GoodsBoardDetailRespDto.builder()
                .boardId(goods.getId())
                .nickname(goods.getMember().getNickname())
//                .profileUrl(goods.getMember()) Todo : 형섭님이 작업하면 채워넣기
                .title(goods.getTitle())
                .category(goods.getGoodsCategory().getKoreaName())
//                .address(goods.getAddress()) Todo : 형섭님이 작업하면 채워넣기
                .description(goods.getContent())
                .price(goods.getPriceBySaleType())
                .createdAt(goods.getCreatedAt())
                .viewCount(goods.getViewCount())
                .goodsImgData(ImageData.from(goods.getImages()))
//                .chatCount(goods.getChatCount())
                .likeCount(goods.getLikes().size())
                .build();
    }
}

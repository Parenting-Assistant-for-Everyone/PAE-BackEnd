package com.pae.server.board.dto.request;

import com.pae.server.board.domain.enums.GoodsCategory;
import com.pae.server.board.domain.enums.SaleType;
import lombok.Builder;

@Builder
public record GoodsBoardRegistReqDto(
        // Todo : 추후 @Authentication.. 애너테이션을 통해 사용자를 가져오는 방식으로 진행해야함.
        Long memberId,

        String title,
        GoodsCategory goodsCategory,
        SaleType saleType,
        long price,
        String description
) {
}

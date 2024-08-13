package com.pae.server.board.dto.request;

import com.pae.server.board.domain.enums.GoodsCategory;

public record GoodsQueryCond(
        GoodsCategory category
) {
}

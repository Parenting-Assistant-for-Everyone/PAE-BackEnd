package com.pae.server.board.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum GoodsCategory {
    CLOTHES("의류"),
    FOODS("음식"),
    DAILY_ITEM("생활용품"),
    TOY("장난감");

    private String koreaName;
}

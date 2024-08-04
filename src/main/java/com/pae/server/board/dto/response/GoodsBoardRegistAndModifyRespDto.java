package com.pae.server.board.dto.response;

import lombok.Builder;

@Builder
public record GoodsBoardRegistAndModifyRespDto(
    Long boardId
) {
}

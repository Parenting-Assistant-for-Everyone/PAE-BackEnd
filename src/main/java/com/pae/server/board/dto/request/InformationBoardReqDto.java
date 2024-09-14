package com.pae.server.board.dto.request;

import lombok.Builder;

@Builder
public record InformationBoardReqDto(
        String url
) {
}

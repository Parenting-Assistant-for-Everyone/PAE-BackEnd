package com.pae.server.board.dto.response;

import lombok.Builder;

@Builder
public record InformationBoardRespDto(
        Long id, String title, String content, String url
) {
}

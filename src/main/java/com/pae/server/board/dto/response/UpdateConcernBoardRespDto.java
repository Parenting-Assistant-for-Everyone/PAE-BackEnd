package com.pae.server.board.dto.response;

import com.pae.server.common.enums.BaseStatus;
import lombok.Builder;

@Builder
public record UpdateConcernBoardRespDto(
        String title, String content, BaseStatus baseStatus, int viewCount
) {
}

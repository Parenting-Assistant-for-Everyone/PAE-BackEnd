package com.pae.server.board.dto.request;

import com.pae.server.common.enums.BaseStatus;
import lombok.Builder;

@Builder
public record ConcernBoardReqDto(
        String title, String content, BaseStatus baseStatus, Long memberId
) {
}

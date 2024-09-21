package com.pae.server.board.dto.response;

import com.pae.server.board.domain.enums.ReviewStatus;
import lombok.Builder;

@Builder
public record ReviewRespDto(
    Long id, String content, int rating, ReviewStatus reviewStatus
) {
}

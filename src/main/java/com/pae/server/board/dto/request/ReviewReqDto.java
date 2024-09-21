package com.pae.server.board.dto.request;

import com.pae.server.board.domain.enums.ReviewStatus;

public record ReviewReqDto (
        String content, int rating, ReviewStatus reviewStatus
) {
}

package com.pae.server.board.converter;

import com.pae.server.board.domain.Review;
import com.pae.server.board.dto.request.ReviewReqDto;
import com.pae.server.board.dto.response.ReviewRespDto;


public class ReviewConverter {

    // Review toEntity
    public static Review toEntity(ReviewReqDto dto) {
        return Review.builder()
                .content(dto.content())
                .rating(dto.rating())
                .status(dto.reviewStatus())
                .build();
    }

    // Review toResponseDto
    public static ReviewRespDto toResponseDto(Review savedReview) {
        return ReviewRespDto.builder()
                .id(savedReview.getId())
                .content(savedReview.getContent())
                .rating(savedReview.getRating())
                .reviewStatus(savedReview.getStatus())
                .build();
    }
}

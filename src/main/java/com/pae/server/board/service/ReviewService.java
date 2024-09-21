package com.pae.server.board.service;

import com.pae.server.board.dto.request.ReviewReqDto;
import com.pae.server.board.dto.response.ReviewRespDto;

import java.util.List;

public interface ReviewService {
    ReviewRespDto saveReview(ReviewReqDto reviewRequestDto);
    ReviewRespDto getReview(Long userId);
    List<ReviewRespDto> getAllReviews();
    List<ReviewRespDto> getWrittenReviews();
    List<ReviewRespDto> getNotWrittenReviews();
    ReviewRespDto updateReview(Long id, ReviewReqDto reviewRequestDto);
    void deleteReview(Long id);
}

package com.pae.server.board.service;

import com.pae.server.board.converter.ReviewConverter;
import com.pae.server.board.domain.Review;
import com.pae.server.board.domain.enums.ReviewStatus;
import com.pae.server.board.dto.request.ReviewReqDto;
import com.pae.server.board.dto.response.ReviewRespDto;
import com.pae.server.board.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    // 리뷰 저장
    public ReviewRespDto saveReview(ReviewReqDto reviewRequestDto) {
        Review review = ReviewConverter.toEntity(reviewRequestDto);
        Review savedReview = reviewRepository.save(review);
        return ReviewConverter.toResponseDto(savedReview);
    }

    // 리뷰 상세 조회
    public ReviewRespDto getReview(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        return ReviewConverter.toResponseDto(review);
    }

    // 리뷰 전체 리스트 조회
    public List<ReviewRespDto> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(ReviewConverter::toResponseDto)
                .collect(Collectors.toList());
    }

    // 작성되지 않은 리뷰 리스트
    public List<ReviewRespDto> getNotWrittenReviews() {
        return reviewRepository.findByStatus(ReviewStatus.NOT_WRITTEN).stream()
                .map(ReviewConverter::toResponseDto)
                .collect(Collectors.toList());
    }

    // 작성된 리뷰 리스트
    public List<ReviewRespDto> getWrittenReviews() {
        return reviewRepository.findByStatus(ReviewStatus.WRITTEN).stream()
                .map(ReviewConverter::toResponseDto)
                .collect(Collectors.toList());
    }

    // 리뷰 수정
    // member 추가한 후 수정해야 함
    public ReviewRespDto updateReview(Long id, ReviewReqDto reviewRequestDto) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        review.update(reviewRequestDto.content(), reviewRequestDto.rating(), reviewRequestDto.reviewStatus());
        Review updatedReview = reviewRepository.save(review);
        return ReviewConverter.toResponseDto(updatedReview);
    }

    // 리뷰 삭제
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

}

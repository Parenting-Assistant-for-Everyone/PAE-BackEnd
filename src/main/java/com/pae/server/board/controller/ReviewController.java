package com.pae.server.board.controller;


import com.pae.server.board.dto.request.ReviewReqDto;
import com.pae.server.board.dto.response.ReviewRespDto;
import com.pae.server.board.service.ReviewServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/my_page/review")
@Slf4j
public class ReviewController {

    @Autowired
    private ReviewServiceImpl reviewService;

    // 리뷰 작성
    @PostMapping("/post")
    public ResponseEntity<String> saveReview(@RequestBody ReviewReqDto reviewRequestDto) {
        reviewService.saveReview(reviewRequestDto);
        return ResponseEntity.ok("리뷰가 성공적으로 작성되었습니다.");
    }

    // 리뷰 조회
    @GetMapping("/view/{id}")
    public ResponseEntity<ReviewRespDto> getReview(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReview(id));
    }

    // 리뷰 리스트 조회
    @GetMapping("/list/all")
    public ResponseEntity<List<ReviewRespDto>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    // 작성되지 않은 리뷰 리스트
    @GetMapping("/list/not-written")
    public ResponseEntity<List<ReviewRespDto>> getNotWrittenReviews() {
        return ResponseEntity.ok(reviewService.getNotWrittenReviews());
    }

    // 작성된 리뷰 리스트
    @GetMapping("/list/written")
    public ResponseEntity<List<ReviewRespDto>> getWrittenReviews() {
        return ResponseEntity.ok(reviewService.getWrittenReviews());
    }

    // 리뷰 수정
    @PutMapping("/update/{id}")
    public ResponseEntity<ReviewRespDto> updateReview(@PathVariable Long id, @RequestBody ReviewReqDto reviewRequestDto) {
        return ResponseEntity.ok(reviewService.updateReview(id, reviewRequestDto));
    }

    // 리뷰 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

}

package com.pae.server.like.controller;

import com.pae.server.board.dto.response.CreateMatchingBoardResDto;
import com.pae.server.common.dto.ApiResponse;
import com.pae.server.common.enums.CustomResponseStatus;
import com.pae.server.like.converter.LikeConverter;
import com.pae.server.like.domain.Like;
import com.pae.server.like.dto.request.CreateLikeReqDto;
import com.pae.server.like.dto.response.CreateLikeResDto;
import com.pae.server.like.service.LikeServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/like")
@RequiredArgsConstructor
@Slf4j
public class LikeController {
    private final LikeServiceImpl likeService;
    //게시판 좋아요(찜) 추가
    @PostMapping("/createLike")
    public ApiResponse<CreateLikeResDto> createLike(@RequestBody CreateLikeReqDto dto){
        Like like = likeService.createLike(dto);
        return ApiResponse.createSuccess(LikeConverter.createLikeResDto(like), CustomResponseStatus.SUCCESS);
    }
    //게시판 좋아요(찜) 취소하기
    @DeleteMapping("/delete/{likeId}")
    public ApiResponse<CreateLikeResDto> deleteLike(@PathVariable Long likeId){
        Like like = likeService.deleteLike(likeId);
        return ApiResponse.createSuccess(LikeConverter.createLikeResDto(like), CustomResponseStatus.SUCCESS);
    }
}

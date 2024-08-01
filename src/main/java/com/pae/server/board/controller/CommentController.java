package com.pae.server.board.controller;

import com.pae.server.board.converter.CommentConverter;
import com.pae.server.board.domain.Comment;
import com.pae.server.board.dto.request.CommentReqDto;
import com.pae.server.board.dto.response.CommentResDto;
import com.pae.server.board.service.CommentService;
import com.pae.server.board.service.CommentServiceImpl;
import com.pae.server.common.dto.ApiResponse;
import com.pae.server.common.enums.CustomResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
@Slf4j
public class CommentController {
    private final CommentServiceImpl commentService;
    //댓글 생성
    @PostMapping("/create")
    public ApiResponse<CommentResDto> createComment(@RequestBody CommentReqDto commentReqDto){
        Comment comment = commentService.createComment(commentReqDto);
        return ApiResponse.createSuccess(CommentConverter.createComment(comment), CustomResponseStatus.COMMENT_CREATE);
    }
    @DeleteMapping("/delete/{commentId}")
    public ApiResponse<CommentResDto> deleteComment(@PathVariable(name = "commentId") Long commentId){
        Comment comment = commentService.deleteComment(commentId);
        return ApiResponse.createSuccess(CommentConverter.createComment(comment), CustomResponseStatus.COMMENT_DELETE);
    }

}

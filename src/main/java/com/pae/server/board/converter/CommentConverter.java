package com.pae.server.board.converter;

import com.pae.server.board.domain.Comment;
import com.pae.server.board.dto.request.CommentReqDto;
import com.pae.server.board.dto.response.CommentResDto;

public class CommentConverter {
    //댓글 생성
    public static Comment createComment(CommentReqDto commentReqDto){
        return Comment.builder()
                .parentId(commentReqDto.parentId())
                .comment(commentReqDto.comment())
                .build();
    }
    public static CommentResDto createComment(Comment comment){
        return CommentResDto.builder()
                .id(comment.getId())
                .ParentId(comment.getParentId())
                .comment(comment.getComment())
                .build();
    }
}

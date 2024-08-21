package com.pae.server.board.converter;

import com.pae.server.board.domain.Comment;
import com.pae.server.board.dto.request.CommentReqDto;
import com.pae.server.board.dto.response.CommentResDto;
import com.pae.server.board.dto.response.CommentResDtoList;
import com.pae.server.member.converter.ChildInformationConverter;

import java.util.List;
import java.util.stream.Collectors;

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
                .writer(comment.getMember().getNickname())
                .build();
    }
    public static CommentResDtoList viewComments(List<Comment> commentList){
        List<CommentResDto> comments = commentList.stream()
                .map(CommentConverter::createComment).collect(Collectors.toList());
        return CommentResDtoList.builder()
                .comments(comments)
                .size(comments.size())
                .build();
    }
}

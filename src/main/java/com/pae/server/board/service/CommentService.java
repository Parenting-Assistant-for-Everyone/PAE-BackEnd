package com.pae.server.board.service;

import com.pae.server.board.domain.Comment;
import com.pae.server.board.dto.request.CommentReqDto;

public interface CommentService {
    Comment createComment(CommentReqDto commentReqDto);
    Comment deleteComment(Long commentId);
}

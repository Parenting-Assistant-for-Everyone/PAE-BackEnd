package com.pae.server.board.service;

import com.pae.server.board.converter.CommentConverter;
import com.pae.server.board.domain.Comment;
import com.pae.server.board.domain.MatchingBoard;
import com.pae.server.board.dto.request.CommentReqDto;
import com.pae.server.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final BoardServiceImpl boardService;
    @Override
    @Transactional
    public Comment createComment(CommentReqDto commentReqDto) {
        Comment comment = CommentConverter.createComment(commentReqDto);
        MatchingBoard matchingBoard = boardService.getMatchingBoard(commentReqDto.boardId());
        comment.setBoard(matchingBoard);
        commentRepository.save(comment);
        return comment;
    }

    @Override
    @Transactional
    public Comment deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).get();
        comment.deleteBoardFromComment();
        commentRepository.delete(comment);
        return comment;
    }


}

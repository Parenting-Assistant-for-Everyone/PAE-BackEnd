package com.pae.server.like.service;

import com.pae.server.board.dto.response.MatchingBoardListDto;
import com.pae.server.like.domain.Like;
import com.pae.server.like.dto.request.CreateLikeReqDto;

import java.util.List;

public interface LikeService {
    Like createLike(CreateLikeReqDto dto); //게시글 좋아요(찜 기능)
    Like deleteLike(Long likeId, Long boardId); //게시글 좋아요 취소(찜 기능 해제)
    List<MatchingBoardListDto> getLikeBoard(); //좋아요(찜)한 게시판 리스트 보기
    boolean checkLike(Long id, Long memberId);
}

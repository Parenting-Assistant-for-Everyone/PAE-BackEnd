package com.pae.server.like.converter;

import com.pae.server.board.domain.Board;
import com.pae.server.like.domain.Like;
import com.pae.server.like.dto.request.CreateLikeReqDto;
import com.pae.server.like.dto.response.CreateLikeResDto;

public class LikeConverter {
    //좋아요(찜) 생성
    public static Like createLike(){
        return Like.builder().
                build();
    }
    public static CreateLikeResDto createLikeResDto(Like like){
        return CreateLikeResDto.builder()
                .id(like.getMember().getId())
                .title(like.getBoard().getTitle())
                .contents(like.getBoard().getContent())
                .viewCount(like.getBoard().getViewCount())
                .likeId(like.getId())
                .build();
    }
}

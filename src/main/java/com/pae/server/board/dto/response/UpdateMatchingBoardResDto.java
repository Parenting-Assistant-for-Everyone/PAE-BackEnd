package com.pae.server.board.dto.response;

import com.pae.server.board.domain.enums.BoardType;
import com.pae.server.board.dto.request.CommentReqDto;
import com.pae.server.common.enums.BaseStatus;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record UpdateMatchingBoardResDto(Long id, String title, String content, Integer viewCount , BaseStatus baseStatus
        , BoardType boardType, String updatedAt, String writer, Integer likes, Integer comments, CommentResDtoList comment
        ,String address, double latitude, double longitude){
}

package com.pae.server.board.converter;

import com.pae.server.board.domain.ConcernBoard;
import com.pae.server.board.dto.request.ConcernBoardReqDto;
import com.pae.server.board.dto.response.ConcernBoardRespDto;
import com.pae.server.board.dto.response.UpdateConcernBoardRespDto;
import com.pae.server.member.domain.Member;


public class ConcernBoardConverter {

    // ConcernBoard toEntity
    public static ConcernBoard toEntity(ConcernBoardReqDto dto) {

        return ConcernBoard.builder()
                .title(dto.title())
                .content(dto.content())
                .baseStatus(dto.baseStatus())
                .viewCount(0)
                .build();
    }

    // ConcernBoard toConcernBoardResponseDto
    public static ConcernBoardRespDto toResponseDto(ConcernBoard savedBoard) {
        return ConcernBoardRespDto.builder()
                .id(savedBoard.getId())
                .title(savedBoard.getTitle())
                .content(savedBoard.getContent())
                .baseStatus(savedBoard.getBaseStatus())
                .viewCount(savedBoard.getViewCount())
                .memberId(savedBoard.getMember().getId())
                .build();
    }

    // ConcernBoard toUpdateConcernBoardResponseDto
    public static UpdateConcernBoardRespDto toUpdateResponseDto(ConcernBoard updatedBoard) {
        return UpdateConcernBoardRespDto.builder()
                .title(updatedBoard.getTitle())
                .content(updatedBoard.getContent())
                .baseStatus(updatedBoard.getBaseStatus())
                .viewCount(updatedBoard.getViewCount())
                .build();
    }
}
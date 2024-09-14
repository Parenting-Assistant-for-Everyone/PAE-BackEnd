package com.pae.server.board.converter;

import com.pae.server.board.domain.InformationBoard;
import com.pae.server.board.dto.request.InformationBoardReqDto;
import com.pae.server.board.dto.response.InformationBoardRespDto;

public class InformationBoardConverter {

    // Information Board toEntity
    public static InformationBoard toEntity(InformationBoardReqDto dto, String title, String content) {
        return InformationBoard.builder()
                .title(title)
                .content(content)
                .build();
    }

    // Information Board toResponseDto
    public static InformationBoardRespDto toResponseDto(InformationBoard savedBoard) {
        return InformationBoardRespDto.builder()
                .id(savedBoard.getId())
                .title(savedBoard.getTitle())
                .content(savedBoard.getContent())
                .build();
    }
}

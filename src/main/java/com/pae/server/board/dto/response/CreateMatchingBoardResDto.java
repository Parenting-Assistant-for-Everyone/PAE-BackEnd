package com.pae.server.board.dto.response;

import com.pae.server.board.domain.enums.BoardType;
import com.pae.server.common.enums.BaseStatus;
import lombok.Builder;

@Builder
public record CreateMatchingBoardResDto(Long id, String title, String content,Integer viewCount ,BaseStatus baseStatus, BoardType boardType) {
}

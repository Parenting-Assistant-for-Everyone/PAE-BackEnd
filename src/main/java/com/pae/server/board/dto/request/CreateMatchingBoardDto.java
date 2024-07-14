package com.pae.server.board.dto.request;

import com.pae.server.board.domain.enums.BoardType;
import com.pae.server.common.enums.BaseStatus;

public record CreateMatchingBoardDto(Long memberId, String title, String content, BaseStatus baseStatus, BoardType boardType)
{
}

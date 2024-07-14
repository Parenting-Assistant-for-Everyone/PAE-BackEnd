package com.pae.server.board.dto.request;

import com.pae.server.common.enums.BaseStatus;
public record UpdateMatchingBoardDto(String title, String content, BaseStatus baseStatus) {
}

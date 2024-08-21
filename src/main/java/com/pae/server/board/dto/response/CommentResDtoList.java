package com.pae.server.board.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record CommentResDtoList(List<CommentResDto> comments, Integer size) {
}

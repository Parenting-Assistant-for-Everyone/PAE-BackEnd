package com.pae.server.board.dto.response;

import lombok.Builder;

@Builder
public record CommentResDto(Long id,Long ParentId,String comment) {
}

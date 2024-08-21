package com.pae.server.board.dto.request;

public record CommentReqDto(Long parentId,Long boardId ,String comment, Long memberId) {
}

package com.pae.server.board.dto.response;

import com.pae.server.board.domain.MatchingBoard;
import lombok.Builder;

import java.util.List;

@Builder
public record MatchingBoardListDto(List<UpdateMatchingBoardResDto> boardList, Integer size,int totalPages, long totalElement) {
}

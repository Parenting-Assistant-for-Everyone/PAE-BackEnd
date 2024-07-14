package com.pae.server.like.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
public record CreateLikeResDto(Long id, String title, String contents, Integer viewCount, Long likeId) {
}

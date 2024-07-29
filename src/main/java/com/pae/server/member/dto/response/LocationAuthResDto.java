package com.pae.server.member.dto.response;

import lombok.Builder;

@Builder
public record LocationAuthResDto(double latitude, double longitude, Long memberId) {
}

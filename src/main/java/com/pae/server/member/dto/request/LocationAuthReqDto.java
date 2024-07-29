package com.pae.server.member.dto.request;

public record LocationAuthReqDto(double latitude, double longitude, Long memberId) {
}

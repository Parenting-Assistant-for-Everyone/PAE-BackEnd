package com.pae.server.member.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record NeighborhoodFinderResDto(Integer size, List<LocationAuthResDto> locationList) {
}

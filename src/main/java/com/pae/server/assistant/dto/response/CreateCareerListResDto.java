package com.pae.server.assistant.dto.response;

import lombok.Builder;

import java.util.List;
@Builder
public record CreateCareerListResDto(Integer size, List<CreateCareerResDto> dto) {
}

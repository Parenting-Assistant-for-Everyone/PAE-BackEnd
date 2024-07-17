package com.pae.server.member.dto.response;

import com.pae.server.member.domain.enums.Gender;
import lombok.Builder;

@Builder
public record CreateChildInformationResDto(Long id, String name, Integer age, Gender gender, String description) {
}

package com.pae.server.member.dto.request;

import com.pae.server.member.domain.enums.Gender;
import lombok.Builder;

@Builder
public record CreateChildInformationReqDto(String name, Integer age, Gender gender, String description, Long memberId) {
}

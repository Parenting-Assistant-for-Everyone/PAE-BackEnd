package com.pae.server.member.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ChildInformationListResDto(Long memberId, Integer size, List<CreateChildInformationResDto> childInformationList){
}

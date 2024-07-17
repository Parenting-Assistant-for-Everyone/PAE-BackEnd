package com.pae.server.member.converter;

import com.pae.server.member.domain.ChildInformation;
import com.pae.server.member.dto.request.CreateChildInformationReqDto;
import com.pae.server.member.dto.response.CreateChildInformationResDto;

public class ChildInformationConverter {
    //육아 정보 생성 converter
    public static ChildInformation createChildInformation(CreateChildInformationReqDto dto){
        return ChildInformation.builder()
                .age(dto.age())
                .name(dto.name())
                .gender(dto.gender())
                .description(dto.description())
                .build();

    }
    public static CreateChildInformationResDto createChildInformationResDto(ChildInformation childInformation){
        return CreateChildInformationResDto.builder()
                .id(childInformation.getId())
                .name(childInformation.getName())
                .age(childInformation.getAge())
                .gender(childInformation.getGender())
                .description(childInformation.getDescription())
                .build();
    }
}

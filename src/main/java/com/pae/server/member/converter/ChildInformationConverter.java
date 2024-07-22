package com.pae.server.member.converter;

import com.pae.server.member.domain.ChildInformation;
import com.pae.server.member.dto.request.CreateChildInformationReqDto;
import com.pae.server.member.dto.response.ChildInformationListResDto;
import com.pae.server.member.dto.response.CreateChildInformationResDto;

import java.util.List;
import java.util.stream.Collectors;

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
    public static ChildInformationListResDto searchChildInformationList(List<ChildInformation> childInformationList,Long memberId){
        List<CreateChildInformationResDto> list = childInformationList.stream()
                .map(ChildInformationConverter::createChildInformationResDto).collect(Collectors.toList());
        return ChildInformationListResDto.builder()
                .memberId(memberId)
                .size(childInformationList.size())
                .childInformationList(list)
                .build();
    }
}

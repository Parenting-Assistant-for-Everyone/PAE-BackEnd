package com.pae.server.assistant.converter;

import com.pae.server.assistant.domain.Career;
import com.pae.server.assistant.dto.response.CreateCareerListResDto;
import com.pae.server.assistant.dto.response.CreateCareerResDto;

import java.util.List;
import java.util.stream.Collectors;

public class CareerConverter {
    public static CreateCareerResDto createCareerResDto(Career career){
        return CreateCareerResDto.builder()
                .description(career.getDescription())
                .startDate(career.getStartDate())
                .endDate(career.getEndDate())
                .build();
    }
    public static CreateCareerListResDto createCareerListResDto(List<Career> careerList){
        List<CreateCareerResDto> dtoList = careerList.stream().map(CareerConverter::createCareerResDto).collect(Collectors.toList());
        return CreateCareerListResDto.builder()
                .size(dtoList.size())
                .dto(dtoList)
                .build();
    }
}

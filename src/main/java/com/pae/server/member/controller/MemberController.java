package com.pae.server.member.controller;

import com.pae.server.common.dto.ApiResponse;
import com.pae.server.common.enums.CustomResponseStatus;
import com.pae.server.member.converter.ChildInformationConverter;
import com.pae.server.member.domain.ChildInformation;
import com.pae.server.member.dto.request.CreateChildInformationReqDto;
import com.pae.server.member.dto.response.CreateChildInformationResDto;
import com.pae.server.member.service.ChildInformationServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final ChildInformationServiceImpl childInformationService;
    @PostMapping("/create")
    public ApiResponse<CreateChildInformationResDto> createChildInformation(@RequestBody CreateChildInformationReqDto dto) {
        ChildInformation childInformation = childInformationService.createChildInformation(dto);
        CreateChildInformationResDto childInformationResDto = ChildInformationConverter.createChildInformationResDto(childInformation);
        return ApiResponse.createSuccess(childInformationResDto, CustomResponseStatus.SUCCESS);
    }
    @PutMapping("/modify/{childInformationId}")
    public ApiResponse<CreateChildInformationResDto> modifyChildInformation(@RequestBody CreateChildInformationReqDto dto, @PathVariable Long childInformationId){
        ChildInformation childInformation = childInformationService.modifyChildInformation(childInformationId,dto);
        CreateChildInformationResDto childInformationResDto = ChildInformationConverter.createChildInformationResDto(childInformation);
        return ApiResponse.createSuccess(childInformationResDto, CustomResponseStatus.SUCCESS);
    }
    @DeleteMapping("/delete/{childInformationId}")
    public ApiResponse<CreateChildInformationResDto> deleteChildInformation(@PathVariable(name = "childInformationId")Long id ){
        ChildInformation childInformation = childInformationService.deleteChildInformation(id);
        CreateChildInformationResDto childInformationResDto = ChildInformationConverter.createChildInformationResDto(childInformation);
        return ApiResponse.createSuccess(childInformationResDto,CustomResponseStatus.SUCCESS);
    }
}

package com.pae.server.assistant.controller;

import com.pae.server.assistant.service.AssistantService;
import com.pae.server.common.dto.ApiResponse;
import com.pae.server.common.enums.CustomResponseStatus;
import com.pae.server.member.converter.MemberConverter;
import com.pae.server.member.dto.request.LocationAuthReqDto;
import com.pae.server.member.dto.response.LocationAuthResDto;
import com.pae.server.member.dto.response.NeighborhoodFinderResDto;
import com.pae.server.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assistant")
@RequiredArgsConstructor
@Slf4j
public class AssistantController {
    private final MemberService memberService;
    private final AssistantService assistantService;
    @PostMapping("/getAddress")
    public ApiResponse<String> getAddress(@RequestBody LocationAuthReqDto dto){
        String address = memberService.getAddress(dto,0);
        return ApiResponse.createSuccess(address, CustomResponseStatus.SUCCESS);
    }
    //초반에 지도 육아도우미 위치 렌더링 할 때, 사용자(육아도우미, 멤버) address 기준으로 설정.
    @GetMapping("/findAtLocation")
    public ApiResponse<NeighborhoodFinderResDto> getAtLocation(@RequestParam(name = "city") String city,
                                                               @RequestParam(name = "district") String district,
                                                               @RequestParam(name = "neighborhood") String neighborhood){
        String fullAddress = String.join(" ", city, district, neighborhood);
        List<LocationAuthResDto> sameLocationResult = memberService.getSameLocationResult(fullAddress, 0);
        return ApiResponse.createSuccess(MemberConverter.getNeighborhoodFinder(sameLocationResult),CustomResponseStatus.SUCCESS);
    }
}

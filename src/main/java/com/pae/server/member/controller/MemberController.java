package com.pae.server.member.controller;

import com.pae.server.assistant.domain.MatchHistory;
import com.pae.server.common.dto.ApiResponse;
import com.pae.server.common.enums.CustomResponseStatus;
import com.pae.server.member.converter.ChildInformationConverter;
import com.pae.server.member.converter.MemberConverter;
import com.pae.server.member.domain.ChildInformation;
import com.pae.server.member.dto.request.CreateChildInformationReqDto;
import com.pae.server.member.dto.request.MatchingReqDto;
import com.pae.server.member.dto.response.ChildInformationListResDto;
import com.pae.server.member.dto.response.CreateChildInformationResDto;
import com.pae.server.member.dto.response.MatchingListResDto;
import com.pae.server.member.dto.response.MatchingResDto;
import com.pae.server.member.service.ChildInformationServiceImpl;
import com.pae.server.member.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final ChildInformationServiceImpl childInformationService;
    private final MemberServiceImpl memberService;
    //아이 정보 생성하기
    @PostMapping("/createChildInformation")
    public ApiResponse<CreateChildInformationResDto> createChildInformation(@RequestBody CreateChildInformationReqDto dto) {
        ChildInformation childInformation = childInformationService.createChildInformation(dto);
        CreateChildInformationResDto childInformationResDto = ChildInformationConverter.createChildInformationResDto(childInformation);
        return ApiResponse.createSuccess(childInformationResDto, CustomResponseStatus.SUCCESS);
    }
    //아이정보 수정하기
    @PutMapping("/modify/{childInformationId}")
    public ApiResponse<CreateChildInformationResDto> modifyChildInformation(@RequestBody CreateChildInformationReqDto dto, @PathVariable Long childInformationId){
        ChildInformation childInformation = childInformationService.modifyChildInformation(childInformationId,dto);
        CreateChildInformationResDto childInformationResDto = ChildInformationConverter.createChildInformationResDto(childInformation);
        return ApiResponse.createSuccess(childInformationResDto, CustomResponseStatus.SUCCESS);
    }
    //아이정보 삭제하기
    @DeleteMapping("/delete/{childInformationId}")
    public ApiResponse<CreateChildInformationResDto> deleteChildInformation(@PathVariable(name = "childInformationId")Long id ){
        ChildInformation childInformation = childInformationService.deleteChildInformation(id);
        CreateChildInformationResDto childInformationResDto = ChildInformationConverter.createChildInformationResDto(childInformation);
        return ApiResponse.createSuccess(childInformationResDto,CustomResponseStatus.SUCCESS);
    }
    //아이 정보 조회하기
    @GetMapping("/view/{memberId}")
    public ApiResponse<ChildInformationListResDto> viewChildInformation(@PathVariable(name = "memberId") Long memberId){
        List<ChildInformation> childInformationList = childInformationService.searchChildInformation(memberId);
        ChildInformationListResDto childInformationListResDto = ChildInformationConverter.searchChildInformationList(childInformationList, memberId);
        return ApiResponse.createSuccess(childInformationListResDto,CustomResponseStatus.SUCCESS);
    }
    //동네위치(실시간) 인증하기
    //육아도우미 매칭 하기
    @PostMapping("/childAssistantMatching")
    public ApiResponse<MatchingResDto> childAssistantMatching(@RequestBody MatchingReqDto dto){
        MatchHistory matchHistory = memberService.match(dto);
        MatchingResDto matchingResDto = MemberConverter.responseMatch(matchHistory);
        return ApiResponse.createSuccess(matchingResDto,CustomResponseStatus.SUCCESS);
    }
    //육아도우미 매칭 삭제
    @DeleteMapping("/deleteMatching/{matchingId}")
    public ApiResponse<MatchingResDto> deleteMatching(@PathVariable(name = "matchingId")Long matchingId){
        MatchHistory matchHistory = memberService.deleteMatching(matchingId);
        return ApiResponse.createSuccess(MemberConverter.responseMatch(matchHistory),CustomResponseStatus.SUCCESS);
    }
    //현재 진행 중인 육아도우미 매칭 조회
    @GetMapping("/viewMatching/{memberId}")
    public ApiResponse<MatchingListResDto> viewMatchList(@PathVariable(name = "memberId")Long memberId,
                                                         @RequestParam(name = "status",required = false)String status){
        List<MatchHistory> matchHistories = memberService.viewMatching(memberId,status);
        return ApiResponse.createSuccess(MemberConverter.viewMatchingList(matchHistories),CustomResponseStatus.SUCCESS);
    }
}

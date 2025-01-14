package com.pae.server.member.service;

import com.pae.server.assistant.domain.MatchHistory;
import com.pae.server.member.dto.request.LocationAuthReqDto;
import com.pae.server.member.dto.request.MatchingReqDto;
import com.pae.server.member.dto.response.LocationAuthResDto;

import java.util.List;

public interface MemberService {
    MatchHistory match(MatchingReqDto dto); //육아도우미 매칭기능
    MatchHistory deleteMatching(Long matchingId); //육아도우미 매칭 삭제, 취소
    List<MatchHistory> viewMatching(Long memberId,String status); //육아도우미 매칭 기록 조회
    String getAddress(LocationAuthReqDto dto, int type); //실시간 주소 조회
    List<LocationAuthResDto> getSameLocationResult(String address, int type);
}

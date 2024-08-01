package com.pae.server.member.service;

import com.pae.server.member.domain.ChildInformation;
import com.pae.server.member.dto.request.CreateChildInformationReqDto;

import java.util.List;

public interface ChildInformationService {
    ChildInformation createChildInformation(CreateChildInformationReqDto dto); //회원 프로필의 아이 정보 추가(생성)
    ChildInformation modifyChildInformation(Long id,CreateChildInformationReqDto dto); //회원 프로필의 아이 정보 수정
    ChildInformation deleteChildInformation(Long id); //회원 프로필의 아이 정보 삭제
    List<ChildInformation> searchChildInformation(Long id); //회원 프로필의 아이 정보 조회
}

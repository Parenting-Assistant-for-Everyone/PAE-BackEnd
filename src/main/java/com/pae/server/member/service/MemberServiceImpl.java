package com.pae.server.member.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pae.server.assistant.domain.Assistant;
import com.pae.server.assistant.domain.MatchHistory;
import com.pae.server.assistant.domain.enums.MatchingStatus;
import com.pae.server.assistant.repository.AssistantRepository;
import com.pae.server.common.enums.CustomResponseStatus;
import com.pae.server.common.exception.CustomException;
import com.pae.server.member.converter.MemberConverter;
import com.pae.server.member.domain.Member;
import com.pae.server.member.dto.request.LocationAuthReqDto;
import com.pae.server.member.dto.request.MatchingReqDto;
import com.pae.server.member.dto.response.KakaoGeocodingResponse;
import com.pae.server.member.dto.response.LocationAuthResDto;
import com.pae.server.member.repository.MatchRepository;
import com.pae.server.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final AssistantRepository assistantRepository;
    private final MatchRepository matchRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper;


    @Value("${kakao.api.key}")
    private String apiKey;

    @Override
    @Transactional
    public MatchHistory match(MatchingReqDto dto) {
        if(!memberRepository.existsById(dto.memberId())){
            throw new CustomException(CustomResponseStatus.MEMBER_NOT_FOUND);
        }
        else if(!assistantRepository.existsById(dto.childAssistantId())){
            throw new CustomException(CustomResponseStatus.CHILD_ASSISTANT_NOT_FOUND);
        }
        else{
            Member member = memberRepository.findById(dto.memberId()).get();
            Assistant assistant = assistantRepository.findById(dto.childAssistantId()).get();
            MatchHistory match = MemberConverter.createMatch(dto);
            match.setMember(member);
            match.setAssistant(assistant);
            matchRepository.save(match);
            return match;
        }
    }

    @Override
    public MatchHistory deleteMatching(Long matchingId) {
        if(!matchRepository.existsById(matchingId)){
            throw new CustomException(CustomResponseStatus.MEMBER_NOT_FOUND);
        }
        else{
            MatchHistory matchHistory = matchRepository.findById(matchingId).get();
            matchRepository.delete(matchHistory);
            return matchHistory;
        }
    }

    @Override
    public List<MatchHistory> viewMatching(Long memberId, String status) {
        if(!matchRepository.existsByMemberId(memberId)){
            throw new CustomException(CustomResponseStatus.MATCHING_NOT_FOUND);
        }
        else{
            switch (status){
                case "EXPIRED" :
                    List<MatchHistory> expiredMatching = matchRepository.findAll().stream().filter(
                            matchHistory -> matchHistory.getMatchingStatus() == MatchingStatus.EXPIRED).collect(Collectors.toList());
                    return expiredMatching;
                case "ONGOING" :
                    List<MatchHistory> onGoingMatching = matchRepository.findAll().stream().filter(
                            matchHistory -> matchHistory.getMatchingStatus() == MatchingStatus.ON_GOING).collect(Collectors.toList());
                    return onGoingMatching;
                default :
                    List<MatchHistory> matchingList = matchRepository.findAll();
                    return matchingList;
            }
        }
    }

    @Override
    @Transactional
    //type = 0 : 육아도우미, type = 1 : 사용자
    public String getAddress(LocationAuthReqDto dto, int type) {
        if(type == 1 && !memberRepository.existsById(dto.memberId())){
            throw new CustomException(CustomResponseStatus.MEMBER_NOT_FOUND);
        }
        if(type == 0 && ! assistantRepository.existsById(dto.memberId())){
            throw new CustomException(CustomResponseStatus.ASSISTANT_NOT_FOUND);
        }


        String url = String.format(
                "https://dapi.kakao.com/v2/local/geo/coord2address.json?x=%f&y=%f",
                dto.longitude(), dto.latitude()
        );

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, String.class
        );

        String jsonResponse = response.getBody();

        try {
            // JSON 문자열을 KakaoGeocodingResponse 객체로 변환
            KakaoGeocodingResponse kakaoResponse = objectMapper.readValue(jsonResponse, KakaoGeocodingResponse.class);

            if (kakaoResponse != null && kakaoResponse.getDocuments() != null && !kakaoResponse.getDocuments().isEmpty()) {
                KakaoGeocodingResponse.Document.Address address = kakaoResponse.getDocuments().get(0).getAddress();

                String region1depthName = address.getRegion1depthName();
                String region2depthName = address.getRegion2depthName();
                String region3depthName = address.getRegion3depthName();
                String fullAddress = String.join(" ", region1depthName, region2depthName, region3depthName);

                if(type == 1){
                    Member member = memberRepository.findById(dto.memberId()).get();
                    member.setAddress(fullAddress,dto.latitude(), dto.longitude());
                    memberRepository.save(member);
                }
                else{
                    Assistant assistant = assistantRepository.findById(dto.memberId()).get();
                    assistant.setAddress(fullAddress,dto.latitude(), dto.longitude());
                    assistantRepository.save(assistant);
                }

                return String.format("%s %s %s",
                        region1depthName, region2depthName, region3depthName);
            } else {
                throw new RuntimeException("위도 경도 값이 한국을 벗어났습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error parsing the response JSON", e);
        }
    }

    @Override
    //type = 0 : 육아도우미, type = 1: 멤버,일반 사용자
    public List<LocationAuthResDto> getSameLocationResult(String address, int type) {
        List<LocationAuthResDto> locationResults = new ArrayList<>();
        System.out.println(address);

        if(type == 1){
            List<Assistant> assistantList = assistantRepository.findAll().stream().filter(assistant -> address.equals(assistant.getAddress()))
                    .collect(Collectors.toList());
            for(Assistant assistant : assistantList){
                LocationAuthResDto locationResult = MemberConverter.getLocationResult(assistant.getLatitude(), assistant.getLongitude(), assistant.getId());
                locationResults.add(locationResult);
            }
        }
        else{
            List<Member> memberList = memberRepository.findAll().stream().filter(member -> address.equals(member.getAddress()))
                    .collect(Collectors.toList());
            for (Member member: memberList) {
                LocationAuthResDto locationResult = MemberConverter.getLocationResult(member.getLatitude(), member.getLongitude(), member.getId());
                locationResults.add(locationResult);
            }
        }
        return locationResults;
    }

}

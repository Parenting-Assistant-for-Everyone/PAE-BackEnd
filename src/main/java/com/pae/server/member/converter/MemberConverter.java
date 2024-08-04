package com.pae.server.member.converter;

import com.pae.server.assistant.converter.CareerConverter;
import com.pae.server.assistant.domain.Assistant;
import com.pae.server.assistant.domain.MatchHistory;
import com.pae.server.assistant.dto.response.CreateCareerListResDto;
import com.pae.server.assistant.repository.AssistantRepository;
import com.pae.server.board.converter.BoardConverter;
import com.pae.server.board.dto.response.AssistantProfileResDto;
import com.pae.server.board.dto.response.MemberProfileResDto;
import com.pae.server.member.domain.Member;
import com.pae.server.member.dto.request.LocationAuthReqDto;
import com.pae.server.member.dto.request.MatchingReqDto;
import com.pae.server.member.dto.response.LocationAuthResDto;
import com.pae.server.member.dto.response.MatchingListResDto;
import com.pae.server.member.dto.response.MatchingResDto;
import com.pae.server.member.dto.response.NeighborhoodFinderResDto;
import com.pae.server.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

public class MemberConverter {
    public static MatchHistory createMatch(MatchingReqDto dto){
        LocalDate today = LocalDate.now();
        Period period = Period.between(today,dto.endDate());
        return MatchHistory.builder()
                .endDate(dto.endDate())
                .remainingDays(period.getDays())
                .matchingStatus(dto.matchingStatus())
                .build();
    }
    public static MatchingResDto responseMatch(MatchHistory matchHistory){
        return MatchingResDto.builder()
                .endDate(matchHistory.getEndDate())
                .memberName(matchHistory.getMember().getNickname())
                .assistantName(matchHistory.getAssistant().getName())
                .remainingDays(matchHistory.getRemainingDays())
                .matchingStatus(matchHistory.getMatchingStatus())
                .build();
    }
    public static MatchingListResDto viewMatchingList(List<MatchHistory> matchHistoryList){
        List<MatchingResDto> dto = matchHistoryList.stream().map(MemberConverter::responseMatch).collect(Collectors.toList());
        return MatchingListResDto.builder()
                .list(dto)
                .size(dto.size())
                .build();
    }
    public static MemberProfileResDto getMemberProfile(Member member){
        return MemberProfileResDto.builder()
                .nickName(member.getNickname())
                .gender(member.getGender())
                .profileUrl(member.getPhotoData().getPhotoUrl())
                .introduce(member.getIntroduce())
                .precautions(member.getPrecautions())
                .cctv(member.getCctv())
                .build();
    }
    public static AssistantProfileResDto getAssistantProfile(Assistant assistant){
        CreateCareerListResDto careerListResDto = CareerConverter.createCareerListResDto(assistant.getCareerList());
        return AssistantProfileResDto.builder()
                .name(assistant.getName())
                .assistantStatus(assistant.getAssistantStatus())
                .certificationStatus(assistant.getCertificationStatus())
                .introduce(assistant.getIntroduce())
                .precaution(assistant.getPrecaution())
                .profileUrl(assistant.getPhotoData().getPhotoUrl())
                .careerList(careerListResDto)
                .build();
    }
    public static LocationAuthResDto getLocationResult(double latitude, double longitude, Long memberId){
        return LocationAuthResDto.builder()
                .memberId(memberId)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
    public static NeighborhoodFinderResDto getNeighborhoodFinder(List<LocationAuthResDto> dto){
        return NeighborhoodFinderResDto.builder()
                .size(dto.size())
                .locationList(dto)
                .build();
    }
}

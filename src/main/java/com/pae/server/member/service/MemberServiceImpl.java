package com.pae.server.member.service;

import com.pae.server.assistant.domain.Assistant;
import com.pae.server.assistant.domain.MatchHistory;
import com.pae.server.assistant.domain.enums.MatchingStatus;
import com.pae.server.assistant.repository.AssistantRepository;
import com.pae.server.common.enums.CustomResponseStatus;
import com.pae.server.common.exception.CustomException;
import com.pae.server.member.converter.MemberConverter;
import com.pae.server.member.domain.Member;
import com.pae.server.member.dto.request.MatchingReqDto;
import com.pae.server.member.repository.MatchRepository;
import com.pae.server.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final AssistantRepository assistantRepository;
    private final MatchRepository matchRepository;
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
}

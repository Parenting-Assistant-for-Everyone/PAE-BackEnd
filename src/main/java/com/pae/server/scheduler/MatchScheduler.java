package com.pae.server.scheduler;

import com.pae.server.assistant.domain.MatchHistory;
import com.pae.server.assistant.domain.enums.MatchingStatus;
import com.pae.server.member.repository.MatchRepository;
import com.pae.server.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class MatchScheduler {
    private final MatchRepository matchRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void checkMatchingDays(){
        LocalDate today = LocalDate.now();
        List<MatchHistory> matchHistoryList = matchRepository.findAll().stream()
                .filter(matching->matching.getMatchingStatus()==MatchingStatus.ON_GOING)
                .collect(Collectors.toList());

        for(MatchHistory matching : matchHistoryList){
            if(today.isBefore(matching.getEndDate())){
                matching.setRemainingDays(matching.getRemainingDays()-1);
                if(matching.getRemainingDays() <0){
                    matching.setMatchingStatus(MatchingStatus.EXPIRED);
                }
            }
        }
        matchRepository.saveAll(matchHistoryList);
    }
}

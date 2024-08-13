package com.pae.server.assistant.domain;

import com.pae.server.assistant.domain.enums.MatchingStatus;
import com.pae.server.common.domain.BaseEntity;
import com.pae.server.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class MatchHistory extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate endDate; //계약 종료 일자
    @Setter
    private Integer remainingDays; //남은 일수
    @Enumerated(EnumType.STRING)
    @Setter
    private MatchingStatus matchingStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "childAssistant_id")
    private Assistant assistant;

    public void setMember(Member member){
        this.member = member;
        member.getMatchHistoryList().add(this);
    }
    public void setAssistant(Assistant assistant){
        this.assistant = assistant;
        assistant.getMatchHistoryList().add(this);
    }
}

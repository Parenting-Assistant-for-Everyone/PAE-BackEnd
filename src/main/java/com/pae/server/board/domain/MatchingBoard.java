package com.pae.server.board.domain;

import com.pae.server.assistant.domain.Assistant;
import com.pae.server.board.domain.enums.BoardType;
import com.pae.server.common.enums.BaseStatus;
import com.pae.server.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@SuperBuilder
@Getter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("Matching")
@Table(name = "MatchingBoard")
public class MatchingBoard extends Board{
    private BoardType boardType; //구인 게시판인지 구직 게시판인지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assistant_id")
    private Assistant assistant;

    public void setAssistant(Assistant assistant){
        this.assistant = assistant;
        assistant.getMatchingBoardList().add(this);
    }


    public void update(String title, String content, BaseStatus baseStatus){
        this.setTitle(title);
        this.setContent(content);
        this.setBaseStatus(baseStatus);
    }
}

package com.pae.server.like.domain;

import com.pae.server.board.domain.Board;
import com.pae.server.board.domain.MatchingBoard;
import com.pae.server.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DynamicInsert
@Builder
@Table(name = "likes")
public class Like {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public void setMember(Member member){
        this.member = member;
        member.getLikes().add(this);
    }
    public void setBoard(Board board){
        this.board = board;
        board.getLikes().add(this);
    }
}

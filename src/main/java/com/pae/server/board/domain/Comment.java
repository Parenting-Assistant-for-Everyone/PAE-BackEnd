package com.pae.server.board.domain;

import com.pae.server.common.domain.BaseEntity;
import com.pae.server.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long parentId; //부모 댓글(대댓글)
    @Column(length = 200)
    private String comment;
    @Column(length = 30)
    private String path;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    public void setBoard(Board board){
        this.board = board;
        board.getComments().add(this);
    }
    public void setMember(Member member){
        this.member = member;
        member.getComments().add(this);
    }
    public void deleteBoardFromComment() {
        if (board != null) {
            board.getComments().remove(this);
            this.board = null;
        }
    }
}

package com.pae.server.board.domain;

import com.pae.server.common.domain.BaseEntity;
import com.pae.server.common.enums.BaseStatus;
import com.pae.server.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@Table(name = "board")
public abstract class Board extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(length = 100)
    private String title;

    @Lob
    private String content;

    @Enumerated(EnumType.STRING)
    private BaseStatus baseStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "board")
    private List<Comment> comments = new ArrayList<>();
    @OneToMany(mappedBy = "board")
    private List<Image> images = new ArrayList<>();

    @ColumnDefault("0")
    private Long viewCount;
}

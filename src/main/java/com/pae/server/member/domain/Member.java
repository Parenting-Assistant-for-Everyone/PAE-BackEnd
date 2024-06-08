package com.pae.server.member.domain;

import com.pae.server.board.domain.Board;
import com.pae.server.common.domain.BaseEntity;
import com.pae.server.common.enums.BaseStatus;
import com.pae.server.image.domain.ImageData;
import com.pae.server.member.domain.enums.AuthStatus;
import com.pae.server.member.domain.enums.Gender;
import com.pae.server.member.domain.oauth.OAuthInfo;
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
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DynamicInsert
@Table(name = "member")
public class Member extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(length = 10)
    private String nickname;

    @Column(length = 250)
    private String email;

    @Column(length = 15)
    private String phone;

    @Embedded
    private OAuthInfo oAuthInfo;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private AuthStatus authStatus;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private BaseStatus memberStatus;

    @Embedded
    private ImageData imageData;

    @ColumnDefault("0")
    private int reportCount;

    @OneToMany(mappedBy = "member")
    private List<MemberRole> roles = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Board> boards = new ArrayList<>();


}


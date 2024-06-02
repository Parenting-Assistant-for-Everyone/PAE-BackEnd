package com.pae.server.member.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberRole {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Builder
    private MemberRole(Member member, Role role) {
        this.member = member;
        this.role = role;
    }

    public static MemberRole of(Member member, Role role) {
        return MemberRole.builder()
                .member(member)
                .role(role)
                .build();
    }
}

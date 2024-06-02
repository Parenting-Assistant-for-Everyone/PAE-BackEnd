package com.pae.server.member.domain;

import com.pae.server.member.domain.enums.RoleType;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private List<MemberRole> members = new ArrayList<>();
}

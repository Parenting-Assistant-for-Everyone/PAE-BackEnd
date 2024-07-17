package com.pae.server.member.domain;

import com.pae.server.common.domain.BaseEntity;
import com.pae.server.member.domain.enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DynamicInsert
@Builder
@Table(name = "ChildInformation")
public class ChildInformation extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20)
    private String name;
    @Column(length = 10)
    private Integer age;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void setMember(Member member){
        this.member = member;
        member.getInformationList().add(this);
    }
    public void update(String name,Integer age, Gender gender,String description){
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.description = description;
    }
}

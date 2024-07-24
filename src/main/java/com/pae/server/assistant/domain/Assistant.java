package com.pae.server.assistant.domain;

import com.pae.server.assistant.domain.enums.AssistantStatus;
import com.pae.server.assistant.domain.enums.AssistantType;
import com.pae.server.assistant.domain.enums.CertificationStatus;
import com.pae.server.assistant.domain.enums.PreferredActivity;
import com.pae.server.common.domain.BaseEntity;
import com.pae.server.image.domain.ImageData;
import com.pae.server.member.domain.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Assistant extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 10)
    private String name;
    @Column(length = 11)
    private String tel;
    @Column(length = 50)
    private String address;
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Gender gender;
    @Column(length = 200)
    private String introduce;
    @Column(length=100)
    private String precaution; //주의사항

    @Embedded
    private ImageData imageData;
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private AssistantStatus assistantStatus;
    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private AssistantType assistantType;
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private CertificationStatus certificationStatus;
    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private PreferredActivity preferredActivity;
    @OneToMany(mappedBy = "assistant")
    private List<MatchHistory> matchHistoryList = new ArrayList<>();
    @OneToMany(mappedBy = "assistant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Career> careerList = new ArrayList<>();

    public void addCareer(Career career) {
        careerList.add(career);
        career.setAssistant(this);
    }
}

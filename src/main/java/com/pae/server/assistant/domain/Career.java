package com.pae.server.assistant.domain;

import com.pae.server.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class Career extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200, nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assistant_id")
    private Assistant assistant;

    public void setAssistant(Assistant assistant){
        this.assistant = assistant;
        assistant.getCareerList().add(this);
    }

}

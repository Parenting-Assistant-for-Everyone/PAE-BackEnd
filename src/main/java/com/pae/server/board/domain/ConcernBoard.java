package com.pae.server.board.domain;

import com.pae.server.common.enums.BaseStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("Concern")
@Table(name = "ConcernBoard")
public class ConcernBoard extends Board{
    public void update(String title, String content, BaseStatus baseStatus){
        this.setTitle(title);
        this.setContent(content);
        this.setBaseStatus(baseStatus);
    }
}


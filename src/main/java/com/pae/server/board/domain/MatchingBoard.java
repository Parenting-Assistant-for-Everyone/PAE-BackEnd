package com.pae.server.board.domain;

import com.pae.server.board.domain.enums.BoardType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("Matching")
@Table(name = "MatchingBoard")
public class MatchingBoard extends Board{
    private BoardType boardType;
}

package com.pae.server.board.domain;

import com.pae.server.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("Concern")
@Table(name = "ConcernBoard")
public class ConcernBoard extends Board{


}

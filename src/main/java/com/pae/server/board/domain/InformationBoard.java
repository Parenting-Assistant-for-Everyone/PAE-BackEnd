package com.pae.server.board.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("Information")
@Table(name = "InformationBoard")
public class InformationBoard extends Board{
}

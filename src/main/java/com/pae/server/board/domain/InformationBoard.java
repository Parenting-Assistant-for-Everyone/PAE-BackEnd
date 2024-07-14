package com.pae.server.board.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("Information")
@Table(name = "InformationBoard")
public class InformationBoard extends Board{
}

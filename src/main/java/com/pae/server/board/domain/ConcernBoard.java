package com.pae.server.board.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("Concern")
@Table(name = "ConcernBoard")
public class ConcernBoard extends Board{



}

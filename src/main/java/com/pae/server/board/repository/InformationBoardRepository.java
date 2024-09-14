package com.pae.server.board.repository;

import com.pae.server.board.domain.InformationBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InformationBoardRepository extends JpaRepository<InformationBoard, Long> {
}

package com.pae.server.board.repository;

import com.pae.server.board.domain.ConcernBoard;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ConcernBoardRepository extends JpaRepository<ConcernBoard,Long> {

}

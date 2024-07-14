package com.pae.server.board.repository;

import com.pae.server.board.domain.Board;
import com.pae.server.board.domain.MatchingBoard;
import com.pae.server.board.domain.enums.BoardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MatchingBoardRepository extends JpaRepository<MatchingBoard,Long> {
    List<MatchingBoard> findAllByBoardType(BoardType boardType);
    List<MatchingBoard> findByTitleContainingOrContentContaining(String titleKeyword, String contentKeyword);

}

package com.pae.server.board.repository;

import com.pae.server.board.domain.Board;
import com.pae.server.board.domain.MatchingBoard;
import com.pae.server.board.domain.enums.BoardType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MatchingBoardRepository extends JpaRepository<MatchingBoard,Long> {
    Page<MatchingBoard> findAllByBoardType(BoardType boardType, PageRequest pageRequest);
    Page<MatchingBoard> findByTitleContainingOrContentContaining(String titleKeyword, String contentKeyword, PageRequest pageRequest);
    Page<MatchingBoard> findAll(Pageable pageable);
}

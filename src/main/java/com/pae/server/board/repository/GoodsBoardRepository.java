package com.pae.server.board.repository;

import com.pae.server.board.domain.GoodsBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsBoardRepository extends JpaRepository<GoodsBoard, Long> {
}

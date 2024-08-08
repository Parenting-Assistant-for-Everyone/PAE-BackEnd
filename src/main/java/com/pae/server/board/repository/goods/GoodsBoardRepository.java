package com.pae.server.board.repository.goods;

import com.pae.server.board.domain.GoodsBoard;
import com.pae.server.board.repository.custom.goods.GoodsBoardCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsBoardRepository extends JpaRepository<GoodsBoard, Long>, GoodsBoardCustomRepository {
}

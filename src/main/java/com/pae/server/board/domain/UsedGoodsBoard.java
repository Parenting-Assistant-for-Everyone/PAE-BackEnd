package com.pae.server.board.domain;

import com.pae.server.board.domain.enums.BoardStatus;
import com.pae.server.board.domain.enums.GoodsCategory;
import com.pae.server.board.domain.enums.SaleType;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("UsedGoods")
@Table(name = "UsedGoodsBoard")
public class UsedGoodsBoard extends Board{
    private BoardStatus boardStatus;
    private SaleType saleType;
    private int price;
    private GoodsCategory goodsCategory;

}

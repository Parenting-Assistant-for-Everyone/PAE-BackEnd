package com.pae.server.board.domain;

import com.pae.server.board.domain.enums.BoardStatus;
import com.pae.server.board.domain.enums.GoodsCategory;
import com.pae.server.board.domain.enums.SaleType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

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

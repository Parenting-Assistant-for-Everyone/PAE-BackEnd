package com.pae.server.board.domain;

import com.pae.server.board.domain.enums.GoodsCategory;
import com.pae.server.board.domain.enums.SaleStatus;
import com.pae.server.board.domain.enums.SaleType;
import com.pae.server.board.dto.request.GoodsBoardModifyReqDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@SuperBuilder
@DynamicInsert
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("UsedGoods")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "GoodsBoard")
public class GoodsBoard extends Board{
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SaleType saleType;

    @ColumnDefault("'ON_SALE'")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SaleStatus saleStatus;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private GoodsCategory goodsCategory;

    public void moidfy(GoodsBoardModifyReqDto modifyDto) {
        setTitle(modifyDto.title());
        setContent(modifyDto.description());

        this.saleType = modifyDto.saleType();
        this.saleStatus = modifyDto.saleStatus();
        this.price = modifyDto.price();
        this.goodsCategory = modifyDto.goodsCategory();
    }

    public void deleteBoard() {
        super.deleteBoard();
    }
}

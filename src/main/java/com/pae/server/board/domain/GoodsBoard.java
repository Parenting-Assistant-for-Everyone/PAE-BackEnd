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

import java.time.Duration;
import java.time.LocalDateTime;

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

    // 무료나눔의 경우 price가 null로 들어가게 됨.
    private Long price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private GoodsCategory goodsCategory;

    // Todo : 게시글이 등록된 지역을 저장해야함

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

    public String getPriceBySaleType() {
        if (this.saleType == SaleType.SALE) {
            return String.valueOf(this.price);
        }
        return "무료나눔";
    }

    public String getDaysAgo(LocalDateTime now) {
        Duration duration = Duration.between(super.getCreatedAt(), now);
        long minutes = duration.toMinutes();
        long hours = duration.toHours();
        long days = duration.toDays();

        if (minutes < 1) {
            return "방금 전";
        } else if (minutes < 60) {
            return minutes + "분 전";
        } else if (hours < 24) {
            return hours + "시간 전";
        } else {
            return days + "일 전";
        }
    }
}

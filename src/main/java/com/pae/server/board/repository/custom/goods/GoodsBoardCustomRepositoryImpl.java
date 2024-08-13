package com.pae.server.board.repository.custom.goods;

import com.pae.server.board.domain.GoodsBoard;
import com.pae.server.board.domain.QGoodsBoard;
import com.pae.server.board.domain.enums.GoodsCategory;
import com.pae.server.board.domain.enums.SaleStatus;
import com.pae.server.board.dto.request.GoodsQueryCond;
import com.pae.server.board.dto.response.GoodsBoardSimpleInfoDto;
import com.pae.server.common.enums.CustomResponseStatus;
import com.pae.server.common.exception.CustomException;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.pae.server.board.domain.QGoodsBoard.*;
import static com.pae.server.like.domain.QLike.*;

@Repository
@RequiredArgsConstructor
public class GoodsBoardCustomRepositoryImpl implements GoodsBoardCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<GoodsBoardSimpleInfoDto> queryGoods(
            Pageable pageable,
            GoodsQueryCond goodsQueryCond
    ) {
        NumberPath<Long> likeCount = Expressions.numberPath(Long.class, "likeCount");

        List<Tuple> fetch = jpaQueryFactory
                .select(
                        goodsBoard,
                        like.countDistinct().as(likeCount)
                )
                .from(goodsBoard)
                .leftJoin(goodsBoard.likes, like)
                .where(
                        goodsBoard.saleStatus.in(SaleStatus.ON_SALE, SaleStatus.RESERVATION),
                        categoryEq(goodsQueryCond.category())
                )
                .groupBy(goodsBoard)
                .fetch();

        List<GoodsBoardSimpleInfoDto> result = new ArrayList<>();
        for (Tuple tuple : fetch) {
            GoodsBoard goods = tuple.get(goodsBoard);
            Long likeNum = tuple.get(likeCount);

            result.add(GoodsBoardSimpleInfoDto.of(goods, Math.toIntExact(likeNum)));
        }

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(goodsBoard.countDistinct())
                .from(goodsBoard)
                .where(
                        categoryEq(goodsQueryCond.category())
                );
        if (countQuery == null) throw new CustomException(CustomResponseStatus.BOARD_NOT_FOUND);

        return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<GoodsBoardSimpleInfoDto> queryLikeGoods(Pageable pageable, Long queryMemberId) {
        NumberPath<Long> likeCount = Expressions.numberPath(Long.class, "likeCount");

        List<Tuple> fetch = jpaQueryFactory
                .select(
                        goodsBoard,
                        like.countDistinct().as(likeCount)
                )
                .from(like)
                .leftJoin(like.board.as(QGoodsBoard.class), goodsBoard)
                .where(like.member.id.eq(queryMemberId))
                .groupBy(goodsBoard)
                .fetch();

        List<GoodsBoardSimpleInfoDto> result = new ArrayList<>();
        for (Tuple tuple : fetch) {
            GoodsBoard goods = tuple.get(goodsBoard);
            Long likeNum = tuple.get(likeCount);

            result.add(GoodsBoardSimpleInfoDto.of(goods, Math.toIntExact(likeNum)));
        }

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(goodsBoard.countDistinct())
                .from(like)
                .where(like.member.id.eq(queryMemberId));
        if (countQuery == null) throw new CustomException(CustomResponseStatus.BOARD_NOT_FOUND);

        return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
    }

    private BooleanExpression categoryEq(GoodsCategory category) {
        return category != null
                ?
                goodsBoard.goodsCategory.eq(category)
                :
                null;
    }
}

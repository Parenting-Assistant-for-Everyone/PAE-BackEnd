package com.pae.server.board.repository.custom.goods;

import com.pae.server.board.domain.GoodsBoard;
import com.pae.server.board.domain.QGoodsBoard;
import com.pae.server.board.domain.enums.GoodsCategory;
import com.pae.server.board.domain.enums.SaleStatus;
import com.pae.server.board.dto.request.GoodsQueryCond;
import com.pae.server.board.dto.response.GoodsBoardSimpleInfoDto;
import com.pae.server.chat.domain.QChatRoom;
import com.pae.server.common.enums.CustomResponseStatus;
import com.pae.server.common.exception.CustomException;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.pae.server.board.domain.QGoodsBoard.*;
import static com.pae.server.chat.domain.QChatRoom.*;
import static com.pae.server.image.domain.QImage.*;
import static com.pae.server.like.domain.QLike.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class GoodsBoardCustomRepositoryImpl implements GoodsBoardCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<GoodsBoardSimpleInfoDto> queryGoods(
            Pageable pageable,
            GoodsQueryCond goodsQueryCond
    ) {
        NumberPath<Long> likeCount = Expressions.numberPath(Long.class, "likeCount");
        NumberPath<Long> chatRoomCount = Expressions.numberPath(Long.class, "chatRoomCount");

        List<Tuple> fetch = jpaQueryFactory
                .select(
                        goodsBoard,
                        like.countDistinct().as(likeCount),
                        image.photoData.photoUrl,
                        // Todo : 채팅방 개수도 select 해와야함.
                        chatRoom.countDistinct().as(chatRoomCount)
                )
                .from(goodsBoard)
                .leftJoin(goodsBoard.likes, like)
                .leftJoin(goodsBoard.images, image).on(image.imageOrder.eq(1))
                .leftJoin(chatRoom).on(chatRoom.goodsBoardId.eq(goodsBoard.id).and(chatRoom.isActivate.isTrue()))
                .where(
                        goodsBoard.saleStatus.in(SaleStatus.ON_SALE, SaleStatus.RESERVATION),
                        categoryEq(goodsQueryCond.category())
                )
                .groupBy(goodsBoard, image)
                .fetch();
        log.info("size : {}", fetch.size());

        List<GoodsBoardSimpleInfoDto> result = generateGoodsSimpleInfoDtoList(fetch, likeCount, chatRoomCount);

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(goodsBoard.countDistinct())
                .from(goodsBoard)
                .where(
                        categoryEq(goodsQueryCond.category())
                );
        if (countQuery.fetchOne() == null) throw new CustomException(CustomResponseStatus.BOARD_NOT_FOUND);

        return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<GoodsBoardSimpleInfoDto> queryLikeGoods(Pageable pageable, Long queryMemberId) {
        NumberPath<Long> likeCount = Expressions.numberPath(Long.class, "likeCount");
        NumberPath<Long> chatRoomCount = Expressions.numberPath(Long.class, "chatRoomCount");

        List<Tuple> fetch = jpaQueryFactory
                .select(
                        goodsBoard,
                        like.countDistinct().as(likeCount),
                        image.photoData.photoUrl,
                        // Todo : 채팅방 개수도 select 해와야함.
                        chatRoom.countDistinct().as(chatRoomCount)
                )
                .from(like)
                .leftJoin(like.board.as(QGoodsBoard.class), goodsBoard)
                .leftJoin(goodsBoard.images, image).on(image.imageOrder.eq(1))
                .leftJoin(chatRoom).on(chatRoom.goodsBoardId.eq(goodsBoard.id))
                .where(like.member.id.eq(queryMemberId))
                .groupBy(goodsBoard, image)
                .fetch();

        List<GoodsBoardSimpleInfoDto> result = generateGoodsSimpleInfoDtoList(fetch, likeCount, chatRoomCount);

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(goodsBoard.countDistinct())
                .from(like)
                .where(like.member.id.eq(queryMemberId));
        if (countQuery == null) throw new CustomException(CustomResponseStatus.BOARD_NOT_FOUND);

        return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<GoodsBoardSimpleInfoDto> queryMyGoods(Pageable pageable, Long queryMemberId) {
        NumberPath<Long> likeCount = Expressions.numberPath(Long.class, "likeCount");
        NumberPath<Long> chatRoomCount = Expressions.numberPath(Long.class, "chatRoomCount");

        List<Tuple> fetch = jpaQueryFactory
                .select(
                        goodsBoard,
                        like.countDistinct().as(likeCount),
                        image.photoData.photoUrl,
                        // Todo : 채팅방 개수도 select 해와야함.
                        chatRoom.countDistinct().as(chatRoomCount)
                )
                .from(goodsBoard)
                .leftJoin(goodsBoard.likes, like)
                .leftJoin(goodsBoard.images, image).on(image.imageOrder.eq(1))
                .leftJoin(chatRoom).on(chatRoom.goodsBoardId.eq(goodsBoard.id))
                .where(goodsBoard.member.id.eq(queryMemberId))
                .groupBy(goodsBoard, image)
                .fetch();

        List<GoodsBoardSimpleInfoDto> result = generateGoodsSimpleInfoDtoList(fetch, likeCount, chatRoomCount);

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(goodsBoard.countDistinct())
                .from(goodsBoard)
                .where(goodsBoard.member.id.eq(queryMemberId));
        if (countQuery == null) throw new CustomException(CustomResponseStatus.BOARD_NOT_FOUND);

        return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
    }

    @Override
    public String getGoodsBoardThumbnail(Long goodsBoardId) {
        return jpaQueryFactory
                .select(image.photoData.photoUrl)
                .from(image)
                .where(image.board.id.eq(goodsBoardId).and(image.imageOrder.eq(1)))
                .fetchOne();
    }

    private List<GoodsBoardSimpleInfoDto> generateGoodsSimpleInfoDtoList(List<Tuple> boardInfos, NumberPath<Long> likeCount, NumberPath<Long> chatRoomCount) {
        List<GoodsBoardSimpleInfoDto> simpleInfoDtos = new ArrayList<>();
        for (Tuple tuple : boardInfos) {
            GoodsBoard goods = tuple.get(goodsBoard);
            long likeNum = tuple.get(likeCount);
            long chatRoomNum = tuple.get(chatRoomCount);
            String thumbnailUrl = tuple.get(image.photoData.photoUrl);

            simpleInfoDtos.add(GoodsBoardSimpleInfoDto.of(goods, Math.toIntExact(likeNum), thumbnailUrl, chatRoomNum));
        }

        return simpleInfoDtos;
    }

    private BooleanExpression categoryEq(GoodsCategory category) {
        return category != null
                ?
                goodsBoard.goodsCategory.eq(category)
                :
                null;
    }
}

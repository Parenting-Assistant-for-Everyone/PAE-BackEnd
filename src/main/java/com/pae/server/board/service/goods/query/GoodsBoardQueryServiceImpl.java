package com.pae.server.board.service.goods.query;

import com.pae.server.board.domain.GoodsBoard;
import com.pae.server.board.dto.request.GoodsQueryCond;
import com.pae.server.board.dto.response.GoodsBoardDetailRespDto;
import com.pae.server.board.dto.response.GoodsBoardSimpleInfoDto;
import com.pae.server.board.repository.goods.GoodsBoardRepository;
import com.pae.server.common.enums.CustomResponseStatus;
import com.pae.server.common.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class GoodsBoardQueryServiceImpl implements GoodsBoardQueryService {
    private final GoodsBoardRepository goodsBoardRepository;

    @Override
    public Page<GoodsBoardSimpleInfoDto> queryGoods(
            Pageable pageable,
            GoodsQueryCond goodsQueryCond
    ) {
        return goodsBoardRepository.queryGoods(pageable, goodsQueryCond);
    }

    @Override
    public GoodsBoardDetailRespDto queryGoodsDetail(
            Long goodsBoardId
    ) {
        GoodsBoard goodsBoard = goodsBoardRepository.findById(goodsBoardId).orElseThrow(
                () -> new CustomException(CustomResponseStatus.BOARD_NOT_FOUND)
        );
        goodsBoard.incrementViewCount();
        return GoodsBoardDetailRespDto.from(goodsBoard);
    }

    @Override
    public Page<GoodsBoardSimpleInfoDto> queryLikeGoods(Pageable pageable, Long queryMemberId) {
        return goodsBoardRepository.queryLikeGoods(pageable, queryMemberId);
    }

    @Override
    public Page<GoodsBoardSimpleInfoDto> queryMyGoods(Pageable pageable, Long queryMemberId) {
        return goodsBoardRepository.queryMyGoods(pageable, queryMemberId);
    }
}

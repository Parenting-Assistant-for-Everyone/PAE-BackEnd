package com.pae.server.board.service.goods.query;

import com.pae.server.board.dto.request.GoodsQueryCond;
import com.pae.server.board.dto.response.GoodsBoardDetailRespDto;
import com.pae.server.board.dto.response.GoodsBoardSimpleInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GoodsBoardQueryService {

    Page<GoodsBoardSimpleInfoDto> queryGoods(
            Pageable pageable,
            GoodsQueryCond queryCond
    );

    GoodsBoardDetailRespDto queryGoodsDetail(
            Long goodsBoardId
    );

    Page<GoodsBoardSimpleInfoDto> queryLikeGoods(
            Pageable pageable,
            Long queryMemberId
    );

    Page<GoodsBoardSimpleInfoDto> queryMyGoods(
            Pageable pageable,
            Long queryMemberId
    );
}

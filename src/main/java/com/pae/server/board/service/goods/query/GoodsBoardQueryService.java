package com.pae.server.board.service.goods.query;

import com.pae.server.board.dto.request.GoodsCategoryCond;
import com.pae.server.board.dto.response.GoodsBoardSimpleInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GoodsBoardQueryService {

    Page<GoodsBoardSimpleInfoDto> queryGoods(
            Pageable pageable,
            GoodsCategoryCond queryCond
    );
}

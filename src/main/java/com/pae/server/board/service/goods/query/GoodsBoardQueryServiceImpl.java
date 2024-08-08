package com.pae.server.board.service.goods.query;

import com.pae.server.board.dto.request.GoodsCategoryCond;
import com.pae.server.board.dto.response.GoodsBoardSimpleInfoDto;
import com.pae.server.board.repository.goods.GoodsBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoodsBoardQueryServiceImpl implements GoodsBoardQueryService {
    private final GoodsBoardRepository goodsBoardRepository;

    @Override
    public Page<GoodsBoardSimpleInfoDto> queryGoods(
            Pageable pageable,
            GoodsCategoryCond categoryCond
    ) {
        Page<GoodsBoardSimpleInfoDto> goodsBoardSimpleInfoDtos = goodsBoardRepository.queryGoods(pageable, categoryCond);
        return goodsBoardSimpleInfoDtos;
    }
}

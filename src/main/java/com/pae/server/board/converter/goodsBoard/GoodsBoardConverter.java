package com.pae.server.board.converter.goodsBoard;

import com.pae.server.board.domain.GoodsBoard;
import com.pae.server.board.dto.request.GoodsBoardModifyReqDto;
import com.pae.server.board.dto.request.GoodsBoardRegistReqDto;
import com.pae.server.board.dto.response.GoodsBoardRegistAndModifyRespDto;
import com.pae.server.member.domain.Member;


public class GoodsBoardConverter {

    public static GoodsBoard RegistDtoToGoodsBoardEntity(GoodsBoardRegistReqDto registReqDto, Member member) {
        return GoodsBoard.builder()
                .title(registReqDto.title())
                .content(registReqDto.description())
                .price(registReqDto.price())
                .saleType(registReqDto.saleType())
                .goodsCategory(registReqDto.goodsCategory())
                .member(member)
                .build();
    }

    public static GoodsBoard ModifyDtoToGoodsBoardEntity(GoodsBoardModifyReqDto modifyReqDto) {
        return GoodsBoard.builder()
                .title(modifyReqDto.title())
                .content(modifyReqDto.description())
                .price(modifyReqDto.price())
                .saleStatus(modifyReqDto.saleStatus())
                .saleType(modifyReqDto.saleType())
                .goodsCategory(modifyReqDto.goodsCategory())
                .build();
    }

    public static GoodsBoardRegistAndModifyRespDto EntityToRegistAndModifyRespDto(GoodsBoard goodsBoard) {
        return GoodsBoardRegistAndModifyRespDto.builder()
                .boardId(goodsBoard.getId())
                .build();
    }
}

package com.pae.server.board.controller.goods;

import com.pae.server.board.dto.request.GoodsBoardModifyReqDto;
import com.pae.server.board.dto.request.GoodsBoardRegistReqDto;
import com.pae.server.board.dto.request.GoodsQueryCond;
import com.pae.server.board.dto.response.GoodsBoardDetailRespDto;
import com.pae.server.board.dto.response.GoodsBoardRegistAndModifyRespDto;
import com.pae.server.board.dto.response.GoodsBoardSimpleInfoDto;
import com.pae.server.board.service.goods.GoodsBoardSerivce;
import com.pae.server.board.service.goods.query.GoodsBoardQueryService;
import com.pae.server.common.dto.ApiResponse;
import com.pae.server.common.enums.CustomResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
@Slf4j
public class GoodsBoardController {
    private final GoodsBoardSerivce goodsBoardSerivce;
    private final GoodsBoardQueryService goodsBoardQueryService;

    @PostMapping("/goods")
    public ResponseEntity<ApiResponse<GoodsBoardRegistAndModifyRespDto>> goodsBoardRegist(
            @RequestPart GoodsBoardRegistReqDto registDto,
            @RequestPart(required = false) List<MultipartFile> images
    ) {
        log.info("registDto : {}", registDto);
        GoodsBoardRegistAndModifyRespDto response = goodsBoardSerivce.goodsBoardRegist(registDto, images);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseStatus.SUCCESS));
    }

    @PutMapping("goods/{goodsId}")
    public ResponseEntity<ApiResponse<GoodsBoardRegistAndModifyRespDto>> goodsBoardModify(
            @PathVariable Long goodsId,
            @RequestPart GoodsBoardModifyReqDto modifyReqDto,
            @RequestPart(required = false) List<MultipartFile> newImages
    ) {
        GoodsBoardRegistAndModifyRespDto response = goodsBoardSerivce.goodsBoardModify(goodsId, modifyReqDto, newImages);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseStatus.SUCCESS));
    }

    @DeleteMapping("goods/{deleteGoodsId}")
    public ResponseEntity<ApiResponse<String>> goodsBoardDelete(
            @PathVariable Long deleteGoodsId
    ) {
        goodsBoardSerivce.goodsBoardDelete(deleteGoodsId);
        return ResponseEntity.ok().body(ApiResponse.createSuccess("게시글 삭제 완료", CustomResponseStatus.SUCCESS));
    }

    /***
     * 거래 게시글 목록 조회
     */

    @GetMapping("/goods")
    public ResponseEntity<ApiResponse<Page<GoodsBoardSimpleInfoDto>>> queryGoods(
            Pageable pageable,
            @ModelAttribute GoodsQueryCond queryCond
    ) {
        log.info("cond : {}", queryCond);
        Page<GoodsBoardSimpleInfoDto> response = goodsBoardQueryService.queryGoods(pageable, queryCond);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseStatus.SUCCESS));
    }

    /***
     *  거래 게시글 상세 조회
     */

    @GetMapping("/goods/{goodsId}")
    public ResponseEntity<ApiResponse<GoodsBoardDetailRespDto>> queryGoodsDetail(
            @PathVariable Long goodsId
    ) {
        GoodsBoardDetailRespDto response = goodsBoardQueryService.queryGoodsDetail(goodsId);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseStatus.SUCCESS));
    }

    /***
     * 찜한 거래 게시글 목록 조회
     */

    @GetMapping("/goods/like")
    public ResponseEntity<ApiResponse<Page<GoodsBoardSimpleInfoDto>>> queryLikeGoods(
            Pageable pageable
    ) {
        // Todo: 추후 @Authenticaion... 을 통해서 memberId를 얻어오도록 변경해얗마.
        Page<GoodsBoardSimpleInfoDto> response = goodsBoardQueryService.queryLikeGoods(pageable, 1L);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseStatus.SUCCESS));
    }

    /***
     * 자신이 올린 거래 게시글 조회
     */

    @GetMapping("/goods/my")
    public ResponseEntity<ApiResponse<Page<GoodsBoardSimpleInfoDto>>> queryMyGoods(
            Pageable pageable
    ) {
        // Todo: 추후 @Authenticaion... 을 통해서 memberId를 얻어오도록 변경해얗마.
        Page<GoodsBoardSimpleInfoDto> response = goodsBoardQueryService.queryMyGoods(pageable, 1L);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseStatus.SUCCESS));
    }
}

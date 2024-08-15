package com.pae.server.board.service.goods;

import com.pae.server.board.converter.goodsBoard.GoodsBoardConverter;
import com.pae.server.board.domain.GoodsBoard;
import com.pae.server.board.dto.request.GoodsBoardModifyReqDto;
import com.pae.server.board.dto.request.GoodsBoardRegistReqDto;
import com.pae.server.board.dto.response.GoodsBoardRegistAndModifyRespDto;
import com.pae.server.board.repository.goods.GoodsBoardRepository;
import com.pae.server.common.enums.CustomResponseStatus;
import com.pae.server.common.exception.CustomException;
import com.pae.server.image.domain.Image;
import com.pae.server.image.domain.PhotoData;
import com.pae.server.image.repository.ImageRepository;
import com.pae.server.image.util.S3Util;
import com.pae.server.member.domain.Member;
import com.pae.server.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GoodsBoardServiceImpl implements GoodsBoardSerivce {
    private final GoodsBoardRepository goodsBoardRepository;
    private final MemberRepository memberRepository;
    private final ImageRepository imageRepository;
    private final S3Util s3Util;

    @Override
    public GoodsBoardRegistAndModifyRespDto goodsBoardRegist(
            // Todo : 추후 @Authentication.. 애너테이션을 통해 사용자를 가져오는 방식으로 진행해야함.
            GoodsBoardRegistReqDto registDto,
            List<MultipartFile> images
    ) {
        Member member = memberRepository.findById(registDto.memberId()).orElseThrow(
                () -> new CustomException(CustomResponseStatus.MEMBER_NOT_FOUND)
        );

        GoodsBoard savedGoodsBoard = goodsBoardRepository.save(
                GoodsBoardConverter.RegistDtoToGoodsBoardEntity(registDto, member)
        );

        if (images != null && !images.isEmpty()) {
            int imageOrder = 1;
            for (MultipartFile image : images) {
                PhotoData photoData = s3Util.uploadFile(image);
                imageRepository.save(Image.of(photoData, savedGoodsBoard, imageOrder));
                imageOrder++;
            }
        }

        return GoodsBoardConverter.EntityToRegistAndModifyRespDto(savedGoodsBoard);
    }

    @Override
    public GoodsBoardRegistAndModifyRespDto goodsBoardModify(Long boardId, GoodsBoardModifyReqDto modifyDto, List<MultipartFile> newImages) {
        GoodsBoard goodsBoard = goodsBoardRepository.findById(boardId).orElseThrow(
                () -> new CustomException(CustomResponseStatus.BOARD_NOT_FOUND)
        );

        memberRepository.findById(modifyDto.memberId()).orElseThrow(
                () -> new CustomException(CustomResponseStatus.MEMBER_NOT_FOUND)
        );

        List<Long> deleteImageIds = modifyDto.deleteImageIdList();
        if (deleteImageIds != null && !deleteImageIds.isEmpty()) {
            deleteImageIds.stream().forEach(imageId -> {
                // Todo : IN 절을 이용하여 최적화 진행해야힘
                Image image = imageRepository.findById(imageId).orElseThrow(
                        () -> new CustomException(CustomResponseStatus.IMAGE_NOT_FOUND)
                );

                s3Util.deleteFile(image.getBucket(), image.getKey());

                imageRepository.delete(image);
            });
        }

        if (newImages != null && !newImages.isEmpty()) {
            newImages.forEach(image -> {
                PhotoData photoData = s3Util.uploadFile(image);

                // Todo : 추후 벌크연산을 통해 쿼리 최소화
//                imageRepository.save(imageRepository.save(Image.of(photoData, goodsBoard)));
            });
        }

        goodsBoard.moidfy(modifyDto);

        return GoodsBoardConverter.EntityToRegistAndModifyRespDto(goodsBoard);
    }

    @Override
    public void goodsBoardDelete(Long boardId) {
        GoodsBoard goodsBoard = goodsBoardRepository.findById(boardId).orElseThrow(
                () -> new CustomException(CustomResponseStatus.BOARD_NOT_FOUND)
        );

        goodsBoard.deleteBoard();
    }
}

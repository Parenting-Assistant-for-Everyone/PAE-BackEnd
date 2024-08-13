package com.pae.server.common.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum CustomResponseStatus {
    // Todo : 우리들만의 code 만들기
    SUCCESS(HttpStatus.OK.value(), "1000", "요청에 성공하였습니다."),
    BOARD_DELETE(HttpStatus.OK.value(), "2001", "게시판을 삭제 하였습니다"),
    COMMENT_CREATE(HttpStatus.OK.value(), "2011", "댓글을 생성하였습니다"),
    COMMENT_DELETE(HttpStatus.OK.value(), "2012", "댓글을 삭제하였습니다"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "6000", "내부 서버 오류입니다."),
    S3_UPLOAD_FAIL(HttpStatus.INTERNAL_SERVER_ERROR.value(), "6001", "S3에 파일을 업로드하지 못했습니다."),
    ALREADY_LIKED(HttpStatus.INTERNAL_SERVER_ERROR.value(),"6011","이미 찜한 게시판입니다"),
    BOARD_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR.value(),"6012","게시판이 존재하지 않습니다."),
    LIKE_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR.value(), "6013","좋아요가 존재하지 않습니다."),
    NOT_OFFER_BOARD(HttpStatus.INTERNAL_SERVER_ERROR.value(), "6014","구인게시글이 아닙니다."),
    MEMBER_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR.value(),"6021","멤버가 존재하지 않습니다."),
    ASSISTANT_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR.value(),"6022","육아도우미가 존재하지 않습니다."),
    IMAGE_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR.value(),"6023","이미지가 존재하지 않습니다."),

    CHILD_ASSISTANT_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR.value(), "6024","육아도우미가 존재하지 않습니다."),
    CHILD_INFORMATION_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR.value(), "6031","아이 정보가 존재하지 않습니다"),
    MATCHING_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR.value(), "6041","매칭 정보가 존재하지 않습니다.");


    private final int httpStatusCode;
    private final String code;
    private final String message;

    CustomResponseStatus(int httpStatusCode, String code, String message) {
        this.httpStatusCode = httpStatusCode;
        this.code = code;
        this.message = message;
    }
}

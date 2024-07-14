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
    BOARD_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR.value(),"6021","게시판이 존재하지 않습니다."),
    MEMBER_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR.value(),"6022","멤버가 존재하지 않습니다."),
    LIKE_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR.value(), "6023","좋아요가 존재하지 않습니다.");


    private final int httpStatusCode;
    private final String code;
    private final String message;

    CustomResponseStatus(int httpStatusCode, String code, String message) {
        this.httpStatusCode = httpStatusCode;
        this.code = code;
        this.message = message;
    }
}

package com.pae.server.member.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum AuthStatus {
    AUTHENTICATED("인증완료"),
    NOT_AUTHENTICATED("미인증");

    private String value;

}

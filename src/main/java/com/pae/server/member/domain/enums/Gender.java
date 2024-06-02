package com.pae.server.member.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Gender {
    MALE("남자"),
    FEMALE("여자"),
    NOT_PROVIDE("제공안함");

    private final String value;
}

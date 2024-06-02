package com.pae.server.common.exception;

import com.pae.server.common.enums.CustomResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException{
    private final CustomResponseStatus customResponseStatus;
}

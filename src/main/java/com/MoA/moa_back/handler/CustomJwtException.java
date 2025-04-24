package com.MoA.moa_back.handler;

import com.MoA.moa_back.common.enums.JwtErrorCode;

public class CustomJwtException extends RuntimeException {

    private final JwtErrorCode errorCode;

    public CustomJwtException(String message, JwtErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public JwtErrorCode getErrorCode() {
        return errorCode;
    }
}

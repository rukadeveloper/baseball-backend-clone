package com.baseball.comics.baseball_comics.layers.Exception.common;

import com.baseball.comics.baseball_comics.layers.enums.common.CommonError;
import lombok.Getter;

@Getter
public class CommonException extends RuntimeException {
    private final CommonError commonError;

    public CommonException(CommonError errorCode) {
        super(errorCode.getMessage());
        this.commonError = errorCode;
    }
}

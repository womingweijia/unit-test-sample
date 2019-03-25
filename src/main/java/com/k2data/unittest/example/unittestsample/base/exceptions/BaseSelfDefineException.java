package com.k2data.unittest.example.unittestsample.base.exceptions;

import com.k2data.unittest.example.unittestsample.base.MessageEnum;

/**
 * Created by cx on 2018-10-10.
 */
public class BaseSelfDefineException extends RuntimeException {
    protected Integer code;

    public BaseSelfDefineException(MessageEnum messageEnum) {
        super(messageEnum.getMessage());
        this.code = messageEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}


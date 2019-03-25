package com.k2data.unittest.example.unittestsample.base.exceptions;

import com.k2data.unittest.example.unittestsample.base.MessageEnum;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by cx on 2018-10-08.
 * 注解式的事务 仅仅在抛出RunTimeException才会回滚
 */
public class InvalidException extends BaseSelfDefineException {

    public InvalidException(MessageEnum msg) {
        super(msg);
    }

}


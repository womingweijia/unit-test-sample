package com.k2data.unittest.example.unittestsample.base.exceptions;

import com.k2data.unittest.example.unittestsample.base.MessageEnum;

/**
 * Created by cx on 2018-10-10.
 */
public class ServiceException extends BaseSelfDefineException {

    public ServiceException(MessageEnum messageEnum) {
        super(messageEnum);
    }
}
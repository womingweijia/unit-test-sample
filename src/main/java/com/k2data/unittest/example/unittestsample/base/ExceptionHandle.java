package com.k2data.unittest.example.unittestsample.base;

import com.k2data.unittest.example.unittestsample.base.exceptions.InvalidException;
import com.k2data.unittest.example.unittestsample.base.exceptions.ServiceException;
import com.k2data.unittest.example.unittestsample.domain.MyResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Created by cx on 2018-10-09.
 */
@RestControllerAdvice
public class ExceptionHandle {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {Exception.class})
    public MyResponseBody handleException(Exception e) {
        logger.info("[系统异常]{}", e);
        return new MyResponseBody(MessageEnum.UNKOWN_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = InvalidException.class)
    public MyResponseBody handleInvalidException(InvalidException e) {
        return new MyResponseBody(e.getCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(value = ServiceException.class)
    public MyResponseBody handleWarningException(ServiceException e) {
        return new MyResponseBody(e.getCode(), e.getMessage());
    }
}

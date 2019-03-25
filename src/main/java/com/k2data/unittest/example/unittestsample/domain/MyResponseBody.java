package com.k2data.unittest.example.unittestsample.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.k2data.unittest.example.unittestsample.base.MessageEnum;

import java.io.Serializable;

/**
 * Created by cx on 2018-10-08.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MyResponseBody<T> implements Serializable{
    private int code;
    private String message;
    private T body;


    public MyResponseBody(MessageEnum msg) {
        this(msg.getCode(), msg.getMessage(), null);
    }

    public MyResponseBody(MessageEnum msg, T body) {
        this(msg.getCode(), msg.getMessage(), body);
    }

    public MyResponseBody(int code, String message) {
        this(code, message, null);
    }

    public MyResponseBody(int code, String message, T body) {
        this.code = code;
        this.message = message;
        this.body = body;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getBody() {
        return body;
    }
}

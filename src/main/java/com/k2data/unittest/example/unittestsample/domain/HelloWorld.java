package com.k2data.unittest.example.unittestsample.domain;

/**
 * Created by cx on 2018-10-18.
 * 用于 PowerMock 的构造器和 final函数测试
 */
public class HelloWorld {
    private String name;

    public HelloWorld(String name) {
        this.name = name;
    }

    public String sayHello () {
        return hello(name);
    }
    private String hello(String sheep) {
        return "Hello "+ sheep;
    }
}

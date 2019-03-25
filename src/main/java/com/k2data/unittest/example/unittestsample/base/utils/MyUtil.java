package com.k2data.unittest.example.unittestsample.base.utils;

import com.k2data.unittest.example.unittestsample.base.MessageEnum;
import com.k2data.unittest.example.unittestsample.base.exceptions.InvalidException;
import com.k2data.unittest.example.unittestsample.domain.HelloWorld;
import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created by cx on 2018-10-16.
 */
public class MyUtil {

    /**
     * 用于计算年龄
     * 用于 PowerMock 静态方法测试：被mock
     * @param birthday
     * @return
     */
    public static Integer getAge(Date birthday) {
        DateTime now = new DateTime();
        DateTime birth = new DateTime(birthday);
        Integer age = now.getYear() - birth.getYear();

        if (age <= 0) {
            throw new InvalidException(MessageEnum.INVALID_BIRTHDAY_ERROR);
        }
        // 算当年生日是否已到
        if (now.getDayOfYear() < birth.getDayOfYear()) {
            age--;
        }
        return age;
    }

    public static String sayHello(String name) {
        return new HelloWorld(name).sayHello();
    }


    public static void main(String[] args) {
        System.out.println(getAge(new Date(new DateTime("1987-04-15").getMillis())));
    }
}

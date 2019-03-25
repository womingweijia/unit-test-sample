package com.k2data.unittest.example.unittestsample;

import com.k2data.unittest.example.unittestsample.base.dao.User;
import com.k2data.unittest.example.unittestsample.base.utils.MyUtil;
import com.k2data.unittest.example.unittestsample.domain.HelloWorld;
import com.k2data.unittest.example.unittestsample.domain.UserResponse;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.text.ParseException;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by cx on 2018-10-18.
 * 使用 PowerMock 做测试
 * https://www.baeldung.com/intro-to-powermock
 * 说明：
 * PowerMock 1.7.4版本有bug, 所以用的是 2.0.0-beta.5
 * 看版本号就知道是不稳定版。相对来讲 mock static 和 constructor 的还比较稳定。
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(MyUtil.class)
public class UsingPowerMockTest {

    /**
     * 静态工具类测试
     * 部分mock使用
     * @throws ParseException
     */
    @Test
    public void testStaticMethod() throws ParseException {

        String birthStr = "1987-10-18";
        Date birth = new Date(new DateTime(birthStr).getMillis());
        // do mocking
        PowerMockito.spy(MyUtil.class); //类中部分被mock
//        PowerMockito.mockStatic(MyUtil.class); //类中所有的都mock
        PowerMockito.when(MyUtil.getAge(birth)).thenReturn(28);

        //调用
        User user = new User("李媛媛", false, "前端", birthStr, "2018-08-19");
        UserResponse userResponse = new UserResponse(user);
        //验证结果
        assertTrue(28 == userResponse.getAge());
//        PowerMockito.verifyStatic(MyUtil.class); //会报错,说不能verify
    }

    @Test
    public void testConstructor() throws Exception {
        String name = "阿尤";
        // do mocking
        HelloWorld mock = PowerMockito.mock(HelloWorld.class);
        PowerMockito.whenNew(HelloWorld.class).withAnyArguments().thenReturn(mock);
        // 调用
        HelloWorld hello = new HelloWorld(name);
        //验证结果
        PowerMockito.verifyNew(HelloWorld.class).withArguments(name);
        assertTrue(hello == mock);
    }

//    @Test //不好用
//    public void testFinalMethod() throws Exception {
//        String name = "阿尤";
//        String result = "欢迎"+name;
////        PowerMockito.spy(HelloWorld.class);
//        HelloWorld mock = PowerMockito.spy(new HelloWorld(name));
//
//        PowerMockito.when(mock, "hello", name).thenReturn(result);
//
//        assertEquals("私有函数测试", mock.sayHello(), result);
//        PowerMockito.verifyPrivate(mock).invoke("hello", name);
//    }
}

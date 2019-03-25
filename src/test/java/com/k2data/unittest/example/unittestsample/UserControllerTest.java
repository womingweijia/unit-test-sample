package com.k2data.unittest.example.unittestsample;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.k2data.unittest.example.unittestsample.base.dao.User;
import com.k2data.unittest.example.unittestsample.base.utils.MyUtil;
import com.k2data.unittest.example.unittestsample.service.UserService;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by cx on 2018-10-10.
 * jsonpath:
 * https://github.com/json-path/JsonPath
 * 参考：
 * https://memorynotfound.com/unit-test-spring-mvc-rest-service-junit-mockito/
 * mock,spy,injectMock, Captor注解的解释：
 * https://www.baeldung.com/mockito-annotations
 * mockito官方文档：
 * http://static.javadoc.io/org.mockito/mockito-core/2.20.0/org/mockito/Mockito.html
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    private static Integer id;
    private final String filepath = this.getClass().getResource("/profile.jpg").getPath();

    @SpyBean
    private UserService userService;

    @Before
    public void before() {
        System.out.println("-----------start -------------");
    }

    /**
     * post 请求 & 获取 response json string
     * @throws Exception
     */
    @Test
    public void testAdd() throws Exception {
        User userInfo = new User("刘明明", false, "研发", "1987-04-15", "2018-03-13");
        String responseString = mvc.perform(MockMvcRequestBuilders.post("/user/add").
                contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(userInfo)))
                .andDo(print()) // 打印出请求和相应的内容
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body.name").value("刘明明"))
                .andReturn().getResponse().getContentAsString();
        id = (Integer) findObject(responseString, "$.body.id");
    }

    /**
     * get 请求: 带参数
     * @throws Exception
     */
    @Test
    public void testGetOne() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/user/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body.name").value("刘明明"))
                .andExpect(jsonPath("$.body.age").value(MyUtil.getAge(new Date(new DateTime("1987-04-15").getMillis()))));
    }

    /**
     * 数组结果的验证
     * @throws Exception
     */
    @Test
    public void testList() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/user/list"))
                .andDo(print()) // 打印出请求和相应的内容
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body").isArray())
                .andExpect(jsonPath("$.body[-1].name").value("刘明明"));

    }


    /**
     * post 请求：上传文件
     * @throws Exception
     */
    @Test
    public void testUpload() throws Exception {
        File localfile = new File(filepath);
        MultipartFile file = new MockMultipartFile(localfile.getName(), new FileInputStream(localfile));
        mvc.perform(MockMvcRequestBuilders.multipart("/user/upload/{id}", id).file("file", file.getBytes()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("成功"));
    }

    /**
     * 下载 & mock示例（可以不mock的）
     * @throws Exception
     */
    @Test
    public void testDownload() throws Exception {
        Mockito.doReturn(filepath).when(userService).download(anyInt());
        System.out.println(filepath);

        ServletOutputStream outputStream = mvc.perform(MockMvcRequestBuilders.get("/user/download/{id}", id))
                .andExpect(status().isOk())
                .andReturn().getResponse().getOutputStream();
        System.out.println(outputStream);
        Assert.assertNotNull(outputStream);
        //检验 download 方法确实被调用 1 次
        Mockito.verify(userService).download(id);
    }

    /**
     * 异常处理
     */
    @Test
    public void testGetOneError() {
        try {
            mvc.perform(MockMvcRequestBuilders.get("/user/{id}", 1000))
                    .andDo(print()) // 打印出请求和相应的内容
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("无数据"));
        } catch (Exception e) {
        }
    }

    public static Object findObject(String jsonString, String jsonPath) throws JsonParseException, JsonMappingException, IOException {
        Object rootObject = new ObjectMapper().readValue(jsonString, HashMap.class);
        return JSONPath.eval(rootObject, jsonPath);
    }
}

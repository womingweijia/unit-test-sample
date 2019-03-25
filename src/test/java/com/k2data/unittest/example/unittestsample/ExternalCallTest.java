package com.k2data.unittest.example.unittestsample;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.k2data.unittest.example.unittestsample.controller.UserController;
import com.k2data.unittest.example.unittestsample.domain.FileStatus;
import com.k2data.unittest.example.unittestsample.service.HdfsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by cx on 2018-10-17.
 * 关于 @Mock 和 @MockBean 的区别：
 * https://stackoverflow.com/questions/44200720/difference-between-mock-mockbean-and-mockito-mock
 * https://www.baeldung.com/java-spring-mockito-mock-mockbean
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ExternalCallTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    // @Mock //不起作用，因为对 Spring 有依赖，只能使用 @MockBean
    private HdfsService hdfsService;

    @InjectMocks
    private UserController userController;


    @Test
    public void testListDir() throws Exception {
        // 构建外部接口期望返回值
        FileStatus fileStatus = new FileStatus("cx", "myDirectory", "DIRECTORY");
        JSONArray FileStatus = new JSONArray();
        FileStatus.add(fileStatus);
        Map<String, Object> parent = new HashMap<>();
        parent.put("FileStatus", FileStatus);
        Map<String, Object> root = new HashMap<>();
        root.put("FileStatuses", new JSONObject(parent));
        JSONObject externalReturn = new JSONObject(root);

        //做 Mock
        Mockito.doReturn(new ResponseEntity<JSONObject>(externalReturn, HttpStatus.OK)).when(hdfsService).listDir("LISTSTATUS");
//        Mockito.when(hdfsService.listDir("LISTSTATUS")).thenReturn(new ResponseEntity<JSONObject>(externalReturn, HttpStatus.OK)); //两种方式都可

        //运行controller中方法
        String response =  mvc.perform(MockMvcRequestBuilders.get("/user/hdfs"))
                .andDo(print()) // 打印出请求和相应的内容
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("获取hdfs根目录信息成功"))
                .andReturn().getResponse().getContentAsString();

        JSONObject body = new JSONObject((Map) UserControllerTest.findObject(response, "$.body"));
        System.out.println(body);
        assertEquals("mock feignClient hdfs service, 验证返回body", externalReturn.toJSONString(), body.toJSONString());
    }

    @Test
    public void testNullBody() throws Exception {
        Mockito.doReturn(new ResponseEntity(null, HttpStatus.OK)).when(hdfsService).listDir("LISTSTATUS");
        mvc.perform(MockMvcRequestBuilders.get("/user/hdfs"))
                .andDo(print()) // 打印出请求和相应的内容
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bdoy").doesNotExist());

    }

    @Test
    public void testError() throws Exception {
        Mockito.doReturn(new ResponseEntity(null, HttpStatus.BAD_REQUEST)).when(hdfsService).listDir("LISTSTATUS");
        mvc.perform(MockMvcRequestBuilders.get("/user/hdfs"))
                .andDo(print()) // 打印出请求和相应的内容
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.bdoy").doesNotExist());

    }
}

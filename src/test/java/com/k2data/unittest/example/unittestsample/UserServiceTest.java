package com.k2data.unittest.example.unittestsample;

import com.k2data.unittest.example.unittestsample.base.dao.User;
import com.k2data.unittest.example.unittestsample.domain.MyResponseBody;
import com.k2data.unittest.example.unittestsample.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by cx on 2018-10-10.
 *
 * assertj 使用文档：
 * http://joel-costigliola.github.io/assertj/index.html
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {


    @Autowired
    private UserService userService;
    private Integer id;

    @BeforeClass
    public static void setUp() {
    }

    @Test
    public void testWorkflow() throws IOException, ParseException {
        //查询列表，确保没有测试数据
        MyResponseBody<List<User>> users = userService.listUsers();
        Assertions.assertThat(users.getBody()).extracting("name")
                .doesNotContain("刘明明");
        int count = users.getBody().size();

        //插入一条数据
        addOne();

        //查询列表，检查插入结果
        users = userService.listUsers();
        Assertions.assertThat(users.getBody()).hasSize(count+1)
                .filteredOn("name", "刘明明")
                .usingFieldByFieldElementComparator();

        //查询
        MyResponseBody<User> user = userService.getOneById(id);
        assertEquals(user.getBody().getId(), id);
        Assertions.assertThat(user.getCode() == 0).isTrue();
        Assertions.assertThat(user.getBody().getUpdatetime())
                .isEqualToIgnoringMinutes(new Date());
        System.out.println(user.getBody().getImg());
        Assertions.assertThat(user.getBody().getImg()).isNull();

        //上传
        String filepath = this.getClass().getResource("/profile.jpg").getPath();
        File localfile = new File(filepath);
        MultipartFile file = new MockMultipartFile(localfile.getName(), new FileInputStream(localfile));
        user = userService.uploadImage(id, file);
        Assertions.assertThat(user.getCode() == 0).isTrue();

        //查询上传结果
        user = userService.getOneById(id);
        Assertions.assertThat(new File(user.getBody().getImg())).canRead();

        //下载
        String outfile = userService.download(id);
        Assertions.assertThat(new File(outfile)).canRead();

        //删除
        userService.deleteOneById(id);
        //查询列表，检验删除结果
        users = userService.listUsers();
        Assertions.assertThat(users.getBody()).hasSize(count)
                .extracting("id").doesNotContain(id);

        //再次删除：异常
        Assertions.assertThatThrownBy(()->userService.deleteOneById(id)).hasMessage("无数据");
    }

    /**
     * 对于异常的测试
     * @throws ParseException
     * @throws FileNotFoundException
     */
    @Test
    public void testFileNotFound() throws ParseException, FileNotFoundException {
        //下载
        Assertions.assertThatThrownBy(()->userService.download(id)).hasMessage("无数据");

    }

    /**
     * 添加一条测试数据
     * @throws ParseException
     */
    private void addOne() throws ParseException {
        User userInfo = new User("刘明明", false, "研发", "1987-04-15", "2018-03-13");
        userService.addUser(userInfo);
        id = userInfo.getId();

    }

}

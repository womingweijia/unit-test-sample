package com.k2data.unittest.example.unittestsample.controller;

import com.alibaba.fastjson.JSONObject;
import com.k2data.unittest.example.unittestsample.base.MessageEnum;
import com.k2data.unittest.example.unittestsample.service.HdfsService;
import com.k2data.unittest.example.unittestsample.base.dao.User;
import com.k2data.unittest.example.unittestsample.domain.MyResponseBody;
import com.k2data.unittest.example.unittestsample.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by cx on 2018-10-08.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private HdfsService hdfsService;

    @RequestMapping({"/", "/list"})
    public MyResponseBody list() {
        return userService.listUsers();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public MyResponseBody newEntity(@RequestBody User user) {
        logger.info(user.toString());
        return userService.addUser(user);
    }
    @RequestMapping(value="/upload/{id}", method = RequestMethod.POST)
    public MyResponseBody uploadImage(@PathVariable Integer id, @RequestParam("file") MultipartFile imgFile) throws IOException {

        return userService.uploadImage(id, imgFile);
    }
    @RequestMapping("/{id}")
    public MyResponseBody getOne(@PathVariable Integer id) {
        return userService.getOneById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public MyResponseBody deleteOne(@PathVariable Integer id) {
        return userService.deleteOneById(id);
    }

    @RequestMapping("/download/{id}")
    public ResponseEntity<InputStreamResource> download(@PathVariable Integer id) throws IOException {
        String filepath = userService.download(id);
        FileSystemResource file = new FileSystemResource(filepath);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getFilename()));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(file.getInputStream()));

    }

    /**
     * 调用外部接口
     * @return
     */
    @RequestMapping("/hdfs")
    public MyResponseBody listDir() {
        ResponseEntity<JSONObject> body = hdfsService.listDir("LISTSTATUS");

        if (body.getStatusCodeValue() == 200) {
            return new MyResponseBody(0, "获取hdfs根目录信息成功", body.getBody());
        }
        return new MyResponseBody(body.getStatusCodeValue(), MessageEnum.EXTERNAL_API_ERROR.getMessage(), body.getBody());
    }

}

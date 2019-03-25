package com.k2data.unittest.example.unittestsample.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by cx on 2018-10-16.
 * 外部调用
 * https://cloud.spring.io/spring-cloud-netflix/multi/multi_spring-cloud-feign.html
 */
@FeignClient(name ="hdfs", url = "${external.hdfs.url}")
public interface HdfsService {

    @RequestMapping(method = RequestMethod.GET, value = "/k2data", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<JSONObject> listDir(@RequestParam("op") String op);
}

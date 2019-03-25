package com.k2data.unittest.example.unittestsample;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
@MapperScan("com.k2data.unittest.example.unittestsample.base.dao")
public class UnitTestSampleApplication  {

	public static void main(String[] args) {
		SpringApplication.run(UnitTestSampleApplication.class, args);
	}
}

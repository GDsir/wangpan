package com.hjk.wangpan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.hjk.wangpan.dao")
public class WangpanApplication {

	public static void main(String[] args) {
		SpringApplication.run(WangpanApplication.class, args);
	}

}

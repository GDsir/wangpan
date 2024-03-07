package com.hjk.wangpan;

import com.hjk.wangpan.utils.SensitiveFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.hjk.wangpan.dao")
public class WangpanApplication {

	public static void main(String[] args) {
		SpringApplication.run(WangpanApplication.class, args);
	}

}

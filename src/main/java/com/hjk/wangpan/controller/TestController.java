package com.hjk.wangpan.controller;

import com.hjk.wangpan.common.Limit;
//import com.example.demo.common.dto.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class TestController {

    @Limit(key = "cachingTest", permitsPerSecond = 1, timeout = 500, msg = "当前排队人数较多，请稍后再试！")
    @GetMapping("cachingTest")
    public List<String> cachingTest() {
        log.info("------读取本地------");
        List<String> list = new ArrayList<>();
        list.add("蜡笔小新");
        list.add("哆啦A梦");
        list.add("四驱兄弟");
        return list;
    }

}

package com.hjk.wangpan.test;


import com.hjk.wangpan.utils.SensitiveFilter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SensitiveFilterTest {

    @Autowired
    SensitiveFilter sensitiveFilter;
    @Test
    public void sensitive(){
    String test="大傻逼";
    String test1="大***";
    char[] testA= sensitiveFilter.filter(test).toCharArray();
    char[] test1A=test1.toCharArray();
    System.out.println(testA);
    System.out.println(test1A);
    Assert.assertArrayEquals(test1A,testA);

    }


}

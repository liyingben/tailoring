package com.tailoring.yewu;

import com.tailoring.SpringBootStartApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringBootStartApplication.class})
public class SpringBootStartApplicationTests extends AbstractJUnit4SpringContextTests {

    @Test
    public void contextLoads() {
    }

}

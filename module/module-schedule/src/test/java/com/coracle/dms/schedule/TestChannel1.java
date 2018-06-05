package com.coracle.dms.schedule;

import com.coracle.yk.xframework.redis.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * Created by huangbaidong on 2016/7/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath:config/applicationContext-schedule.xml",
        "classpath:config/applicationContext-schedule-dubbo.xml",
        "classpath:config/applicationContext-jedis.xml",
        })
public class TestChannel1 {

    /*@Resource
    private SchedulerHelper schedulerHelper;

    @Resource
    private RedisUtil redisUtil;*/

    /*@Test
    public void test() throws InterruptedException {
        System.out.println(redisUtil.setnx("haha", "1000"));
        System.out.println(redisUtil.getSet("haha", "3002"));
        System.out.println(redisUtil.getInExecute("haha"));
        redisUtil.set("hehe", "999");
        redisUtil.remove("haha");
        System.out.println(redisUtil.getInExecute("haha"));
        System.out.println(redisUtil.get("hehe"));
    }
*/
    @Test
    public void test2() throws IOException {
        final MyTest test = new MyTest();
        new Thread(new Runnable() {
            @Override
            public void run() {

                while(true) {
                    test.syncTest("thread 1");
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {

                while(true) {
                    test.syncTest("thread 2");
                }
            }
        }).start();

        System.in.read();

    }




}

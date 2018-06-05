package com.coracle.dms.schedule;

import com.coracle.dms.schedule.quartz.SchedulerHelper;
import com.coracle.yk.xframework.redis.RedisUtil;
import com.coracle.yk.xframework.redis.lock.IDGenerator;
import com.coracle.yk.xframework.redis.lock.Lock;
import com.coracle.yk.xframework.redis.lock.RedisBasedDistributedLock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by huangbaidong on 2016/7/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath:config/applicationContext-*.xml"
        })
public class TestChannel2 {

    private static Set<String> generatedIds = new HashSet<String>();

    private static final String LOCK_KEY = "lock.lock";
    private static final long LOCK_EXPIRE = 5 * 1000;
    @Resource
    private SchedulerHelper schedulerHelper;

    @Resource
    private RedisUtil redisUtil;

    @Test
    public void test() throws InterruptedException, IOException {
        SocketAddress addr = new InetSocketAddress("localhost", 9999);

        Lock lock1 = new RedisBasedDistributedLock(redisUtil, LOCK_KEY, LOCK_EXPIRE, addr);
        IDGenerator g1 = new IDGenerator(lock1);
        IDConsumeTask consume1 = new IDConsumeTask(g1, "consume1");

        Lock lock2 = new RedisBasedDistributedLock(redisUtil, LOCK_KEY, LOCK_EXPIRE, addr);
        IDGenerator g2 = new IDGenerator(lock2);
        IDConsumeTask consume2 = new IDConsumeTask(g2, "consume2");

        Thread t1 = new Thread(consume1);
        Thread t2 = new Thread(consume2);
        t1.start();
        t2.start();

        Thread.sleep(20 * 1000); //让两个线程跑20秒

        IDConsumeTask.stopAll();

        t1.join();
        t2.join();
    }

    static String time() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    static class IDConsumeTask implements Runnable {

        private IDGenerator idGenerator;

        private String name;

        private static volatile boolean stop;

        public IDConsumeTask(IDGenerator idGenerator, String name) {
            this.idGenerator = idGenerator;
            this.name = name;
        }

        public static void stopAll() {
            stop = true;
        }

        public void run() {
            System.out.println(time() + ": consume " + name + " start ");
            while (!stop) {
                String id = idGenerator.getAndIncrement();
                if (id != null) {
                    if(generatedIds.contains(id)) {
                        System.out.println(time() + ": duplicate id generated, id = " + id);
                        stop = true;
                        continue;
                    }

                    generatedIds.add(id);
                    System.out.println(time() + ": consume " + name + " add id = " + id);
                }
            }
            // 释放资源
            idGenerator.release();
            System.out.println(time() + ": consume " + name + " done ");
        }

    }

}

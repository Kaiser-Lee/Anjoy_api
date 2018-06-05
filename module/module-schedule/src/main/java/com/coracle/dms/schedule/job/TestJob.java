package com.coracle.dms.schedule.job;

import org.springframework.stereotype.Component;

/**
 * Created by 龙顺林
 * 新增定时器，如同TestJob一样新增类，再增加方法，也可以一个类中加多个定时方法
 * testOne和testTwo为定时任务
 * 如果需要调用其他暴露的service需要在applicationContext-schedule-dubbo.xml中如同xweb中的applicationContext-xweb-dubbo.xml一样增加对应的service配置
 *
 * 2017/3/29.
 */
@Component
public class TestJob {

    int count = 0;

    /**
     * 检查支付超时
     */
    public void execute() {
        int temp = count++;
        System.out.println("count : "+temp);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("temp : "+temp);
    }

    /**
     * 测试任务一
     */
    public void testOne() {
        System.out.println("Test One");
    }


    /**
     * 测试任务二
     */
    public void testTwo() {
        System.out.println("Test Two");
    }
}

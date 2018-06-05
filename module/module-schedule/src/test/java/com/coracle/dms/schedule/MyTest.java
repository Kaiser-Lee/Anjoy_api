package com.coracle.dms.schedule;

/**
 * Created by huangbaidong
 * 2017/4/1.
 */
public class MyTest {
    boolean isLock = false;
    public void syncTest(String name) {
        System.out.println(name+" try to sync start...");
        if(!isLock) {
            isLock = true;
            try {
                System.out.println(name+" get lock sleep 100");
                Thread.sleep(100);
                System.out.println(name + " try to sync end...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            isLock = false;
        }

    }


}

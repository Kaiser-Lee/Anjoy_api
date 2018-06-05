package com.xiruo.medbid.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 订单号生成工具
 */
public class OrderRandomUtils {
    private static int seed = 0;
    private static final String DATE_PATTERN="yyMMddHHmmss";
  
    /**
     * 订单号生成
     * @author tanyb
     * @param length 规则码长度
     * @return
     */
    public synchronized static String genDateAlis(int length) {
        if (seed < 9999) { //防止在高并发的情况下出现多个相同的订单号
            seed++;
        } else {
            seed = 1;
        }
        
        String dateString = "";
        if(length==18){
        	dateString = (new SimpleDateFormat(DATE_PATTERN)).format(new Date());
        	dateString = dateString + String.format("%04d", seed) + String.format("%02d", new Random().nextInt(99));
        }
        return dateString;
    }

    /**
     * 生成单号
     * @return
     */
    public static String generateOrderCode() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String code = dateFormat.format(new Date());
        return code;
    }

}


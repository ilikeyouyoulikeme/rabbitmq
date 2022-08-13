/* 王其康版权所有
 */
package com.wqk.rabbitmq.util;

/**
 * @author wangqikang
 * @version 1.0
 */
public class SleepUtils {
    public static void sleep(int second){
        try {
            Thread.sleep(1000*second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

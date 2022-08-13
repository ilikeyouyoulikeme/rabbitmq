/* 王其康版权所有
 */
package com.wqk.rabbitmq.five;

import com.rabbitmq.client.Channel;
import com.wqk.rabbitmq.util.RabbitMqUtils;

import java.util.Scanner;

/**
 * @author wangqikang
 * @version 1.0
 * 发消息给交换机
 */
public class EmitLog {
    private static final String  EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            String message = scanner.next();
            channel.basicPublish(EXCHANGE_NAME,"",null,message.getBytes());
            System.out.println("生产者发出消息!");

        }


    }
}

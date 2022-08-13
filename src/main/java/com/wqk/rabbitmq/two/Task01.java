/* 王其康版权所有
 */
package com.wqk.rabbitmq.two;

import com.rabbitmq.client.Channel;
import com.wqk.rabbitmq.util.RabbitMqUtils;

import java.util.Scanner;

/**
 * @author wangqikang
 * @version 1.0
 * 生产者发送大量消息
 */
@SuppressWarnings("all")
    public class Task01 {
    public static final String QUEUE_NAME = "hello1";
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //从控制台中接受信息
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            String message = scanner.next();
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
            System.out.println("发送消息完成！");
        }

    }
}

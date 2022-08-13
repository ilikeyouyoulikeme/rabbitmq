/* 王其康版权所有
 */
package com.wqk.rabbitmq.three;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.wqk.rabbitmq.util.RabbitMqUtils;

import java.util.Scanner;

/**
 * @author wangqikang
 * @version 1.0
 * 消息手动应答不丢失，放回队列重新消费
 */
@SuppressWarnings("All")
    public class Task2 {
    public static final String QUEUE_NAME = "hello1";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqUtils.getChannel();
        //设置发布确认
        channel.confirmSelect();
        //声明一个队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //从控制台中接受信息
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            String message = scanner.next();
            //消息持久化
            channel.basicPublish("",QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
            System.out.println("发送消息完成！");
        }
    }

}

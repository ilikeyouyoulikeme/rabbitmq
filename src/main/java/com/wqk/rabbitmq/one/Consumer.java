/* 王其康版权所有
 */
package com.wqk.rabbitmq.one;

import com.rabbitmq.client.*;

/**
 * @author wangqikang
 * @version 1.0
 * 消费者 接收消息的
 */
@SuppressWarnings("All")
public class Consumer {
    //队列名称
    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        //设置工厂的ip连接MQ的队列
        factory.setHost("192.168.229.222");
        //设置用户名
        factory.setUsername("admin");
        factory.setPassword("123");
        // 创建连接
        Connection connection = factory.newConnection();
        //获取信道
        Channel channel = connection.createChannel();
        /**
         * 消费者消费消息
         * 1 消费那个队列
         * 2 消费成功后是否自动应答，true代表自动应答，false手动应答
         * 3 消费者未成功消费的回调
         * 4 消费者取消费的回调
         */
        //声明接受消息
        DeliverCallback deliverCallback = ( consumerTag, message)->{
            System.out.println(new String (message.getBody()));
        };
        //取消消息时的回调
        CancelCallback cancelCallback = consumerTag->{
            System.out.println("消息消费被中断");
        };
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);

    }
}

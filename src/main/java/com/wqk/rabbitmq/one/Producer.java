/* 王其康版权所有
 */
package com.wqk.rabbitmq.one;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author wangqikang
 * @version 1.0
 * 发消息
 */
@SuppressWarnings("all")
public class Producer {
    // 队列名称
    public static final String QUEUE_NAME ="hello";
    // 发消息
    public static void main(String[] args) throws Exception{
        // 创建一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置工厂的ip连接MQ的队列
        factory.setHost("192.168.229.223");
        //设置用户名
        factory.setUsername("admin");
        factory.setPassword("123");
        // 创建连接
        Connection connection = factory.newConnection();
        //获取信道
        Channel channel = connection.createChannel();
        /**
         * 生成一个队列
         * 1 队列名称
         * 2 队列里面的消息是否持久化，默认情况下消息存储内存中
         * 3 该队列是否只供一个消费者进行消费，是否进行消息共享，true表示可以多个消费者消费
         * 默认情况不允许多个消费者消费x
         * 4 表示是否自动删除，最后一个消费者断开连接以后，该队列是否自动删除，true表示自动删除，反之亦然
         * 5 其他参数*/
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        String message = "hello world";
        /**
         * 1 发送到那个交换机
         * 2 路由的key值是那个，本次是队列名称
         * 3 其他参数信息
         * 4 发送消息的消息体
         */
        channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
        System.out.println("消息发送完毕！");


    }
}

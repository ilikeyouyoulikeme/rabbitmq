/* 王其康版权所有
 */
package com.wqk.rabbitmq.util;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author wangqikang
 * @version 1.0
 * 工具类：创建信道
 */
public class RabbitMqUtils {
    public static Channel getChannel() throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        //设置工厂的ip连接MQ的队列
        factory.setHost("192.168.229.232");
        //设置用户名
        factory.setUsername("admin");
        factory.setPassword("123");
        // 创建连接
        Connection connection = factory.newConnection();
        //获取信道
        com.rabbitmq.client.Channel channel = connection.createChannel();
        return channel;
    }
}

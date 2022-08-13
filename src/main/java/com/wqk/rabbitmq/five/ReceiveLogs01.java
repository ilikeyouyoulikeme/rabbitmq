/* 王其康版权所有
 */
package com.wqk.rabbitmq.five;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.wqk.rabbitmq.util.RabbitMqUtils;

/**
 * @author wangqikang
 * @version 1.0
 * 消息的接受
 */
public class ReceiveLogs01 {
    //交换机的名称
    private static final String  EXCHANGE_NAME = "logs";
    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqUtils.getChannel();
        //声明一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        //声明一个临时队列,队列的名称是随机的
        // 消费者断开连接时，队列删除
        String queueName = channel.queueDeclare().getQueue();
        //绑定交换机与队列
        channel.queueBind(queueName,EXCHANGE_NAME,"");
        System.out.println("ReceiveLogs01等待接受消息，把接受到的消息打印在屏幕上.......");
        //声明接受消息
        DeliverCallback deliverCallback = (consumerTag, message)->{
            System.out.println(new String (message.getBody()));
        };
        channel.basicConsume(queueName,true,deliverCallback,consumerTag ->{});


    }
}

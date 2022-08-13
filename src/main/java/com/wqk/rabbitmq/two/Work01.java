/* 王其康版权所有
 */
package com.wqk.rabbitmq.two;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.wqk.rabbitmq.util.RabbitMqUtils;

/**
 * @author wangqikang
 * @version 1.0
 * 一个工作线程
 */
@SuppressWarnings("all")
public class Work01  {
    //队列的名称
    public static final String QUEUE_NAME ="hello1";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqUtils.getChannel();
        DeliverCallback deliverCallback = (consumerTag,message)->{
            System.out.println("接收到的消息"+new String(message.getBody()));
        };
        //消息接受取消的时
        CancelCallback cancelCallback = consumerTag->{
            System.out.println(consumerTag+"消息者取消消息接口回调逻辑");
        };
        System.out.println("C2等待接受消息......");
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);


    }
}

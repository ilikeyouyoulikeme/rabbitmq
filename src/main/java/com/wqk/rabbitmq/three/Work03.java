/* 王其康版权所有
 */
package com.wqk.rabbitmq.three;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.wqk.rabbitmq.util.RabbitMqUtils;
import com.wqk.rabbitmq.util.SleepUtils;

/**
 * @author wangqikang
 * @version 1.0
 */
public class Work03 {
    public static final String QUEUE_NAME = "hello1";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        System.out.println("C1等待处理时间较短!");
        //声明一个队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        DeliverCallback deliverCallback = (consumerTag, message)->{
            SleepUtils.sleep(1);
            System.out.println("接收到的消息"+new String(message.getBody()));
            //1 表示消息的标记tag 2 是否批量应答
            channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
        };
        //消息接受取消的时
        CancelCallback cancelCallback = consumerTag->{

            System.out.println(consumerTag+"消息者取消消息接口回调逻辑");
        };
        //设置不公平分发
         channel.basicQos(1);
        channel.basicConsume(QUEUE_NAME,deliverCallback,cancelCallback);
    }
}

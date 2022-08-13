/* 王其康版权所有
 */
package com.wqk.rabbitmq.six;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.wqk.rabbitmq.util.RabbitMqUtils;

/**
 * @author wangqikang
 * @version 1.0
 */
public class ReciveLogsDirect02 {
    public static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqUtils.getChannel();
        //声明一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        channel.queueDeclare("disk",false,false,false,null);
        channel.queueBind("disk",EXCHANGE_NAME,"error");
        //声明接受消息
        DeliverCallback deliverCallback = (consumerTag, message)->{
            System.out.println("02打印消息"+new String (message.getBody()));
        };
        channel.basicConsume("console",true,deliverCallback,consumerTag -> {});
    }
}

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
public class ReciveLogsDirect01 {
    public static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqUtils.getChannel();
        //声明一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        channel.queueDeclare("console",false,false,false,null);
        channel.queueBind("console",EXCHANGE_NAME,"info");
        channel.queueBind("console",EXCHANGE_NAME,"Warning");
        //声明接受消息
        DeliverCallback deliverCallback = (consumerTag, message)->{
            System.out.println(new String (message.getBody()));
        };
        channel.basicConsume("console",true,deliverCallback,consumerTag -> {});
    }
}

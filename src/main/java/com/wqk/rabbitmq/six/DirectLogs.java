/* 王其康版权所有
 */
package com.wqk.rabbitmq.six;

import com.rabbitmq.client.Channel;
import com.wqk.rabbitmq.util.RabbitMqUtils;

import java.util.Scanner;

/**
 * @author wangqikang
 * @version 1.0
 */
public class DirectLogs {
    public static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            String message = scanner.next();
            channel.basicPublish(EXCHANGE_NAME,"info",false,null,message.getBytes());

        }

    }
}

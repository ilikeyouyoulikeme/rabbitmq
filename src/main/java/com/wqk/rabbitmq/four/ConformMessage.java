/* 王其康版权所有
 */
package com.wqk.rabbitmq.four;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.wqk.rabbitmq.util.RabbitMqUtils;

import java.util.UUID;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author wangqikang
 * @version 1.0
 * 发布确认模式
 *   比较时间确认那种方式是最好的
 *   1单个确认
 *   2 批量确认
 *   3 异步批量确认
 */
@SuppressWarnings("all")
public class ConformMessage {
    //批量发消息的个数
    public static int message_count = 1000;

    public static void main(String[] args) throws Exception {
        publishMessageIndividual(); //发布1000条单独确认消息耗时557ms
    }
    // 单个确认
    public static void publishMessageIndividual() throws Exception{
        Channel channel = RabbitMqUtils.getChannel();
        String queue_name = UUID.randomUUID().toString();
        channel.queueDeclare(queue_name,true,false,false,null);
        //开启发布确认
        channel.confirmSelect();
        //开始时间
        long begin = System.currentTimeMillis();
        //批量发消息
        for (int i = 0; i < message_count; i++) {
            String message = i+"";
            channel.basicPublish("",queue_name,null,message.getBytes());
            //单个消息马上进行发布确认
            boolean flag = channel.waitForConfirms();
            if(flag){
                System.out.println("消息发送成功");
            }

        }
        long end = System.currentTimeMillis();
        System.out.println("发布1000条单独确认消息耗时"+(end-begin)+"ms");
    }
    //批量发布确认
    public static void publishMessageBatch() throws Exception{
        Channel channel = RabbitMqUtils.getChannel();
        String queue_name = UUID.randomUUID().toString();
        channel.queueDeclare(queue_name,true,false,false,null);
        //开启发布确认
        channel.confirmSelect();
        //开始时间
        long begin = System.currentTimeMillis();
        //批量确认消息大小
        int batchSize = 100;
        //批量发消息,批量发布确认
        for (int i = 0; i < message_count; i++) {
            String message = i+"";
            channel.basicPublish("",queue_name,null,message.getBytes());
            if((i+1)%100 ==0){
                channel.waitForConfirms();
            }
        }

        long end = System.currentTimeMillis();
        System.out.println("发布1000条单独确认消息耗时"+(end-begin)+"ms");

    }
    //异步发布确认
    public static void publishMessageAsync() throws  Exception{
        Channel channel = RabbitMqUtils.getChannel();
        String queue_name = UUID.randomUUID().toString();
        channel.queueDeclare(queue_name,true,false,false,null);
        //开启发布确认
        channel.confirmSelect();
        /*
         * 线程安全有序的hash表，适用于高并发的情况下
         * 1 轻松的将序号与消息进行关联
         * 2 轻松的批量删除条目，只要给到序号
         * 3 支持高并发
         */
        ConcurrentSkipListMap<Long, String> map = new ConcurrentSkipListMap<>();
        //准备消息监听器，监听那些消息成功了，那些消息失败了
        ConfirmCallback ackCallback = (deliveryTag, multiple)->{
            if(multiple){
                ConcurrentNavigableMap<Long, String> map1 = map.headMap(deliveryTag);
                map1.clear();
            }else{
                map.remove(deliveryTag);
            }
            System.out.println("确认的消息"+deliveryTag);
        };
        ConfirmCallback nackCallback = (deliveryTag, multiple)->{
            String message = map.get(deliveryTag);
            System.out.println("未确认的消息"+message+"标记为:"+deliveryTag);
        };
        channel.addConfirmListener(ackCallback,nackCallback);
        //开始时间
        long begin = System.currentTimeMillis();
        for (int i = 0; i < message_count; i++) {
            String message = i+"";
            channel.basicPublish("",queue_name,null,message.getBytes());
            map.put(channel.getNextPublishSeqNo(),message);
            
        }
        long end = System.currentTimeMillis();
        System.out.println("异步发送消息耗时"+(end-begin)+"ms");
    }
}

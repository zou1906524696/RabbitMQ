package com.zzf.rabbitmq.amqpclient;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitProducer {
    private static final String EXCHANGE_NAME = "exchange_name";
    private static final String ROUTING_KEY = "routingkey_demo";
    private static final String QUEUE_NAME = "queue_demo";
    private static final String IP_ADDRESS = "148.70.22.234";
    private static final int PORT = 5672;

    public static void main(String[] args) throws IOException, TimeoutException {
        //1 创建一个connectionFactory
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(IP_ADDRESS);
        factory.setPort(PORT);
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");
        //2 通过连接工厂创建连接
        Connection connection = factory.newConnection();
        //3 通过connection创建一个Channel
        Channel channel = connection.createChannel();
        //创建一个type=direct、持久化的、非自动删除的交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"direct",true,false,null);
        //创建一个持久化、非排他的、非自动删除的队列
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        //将交换机和队列通过路由键绑定
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,ROUTING_KEY);

        //发送一条持久化的消息
        for (int i = 0; i < 10; i++) {
            String message = "Hello RabbitMq" + i;
            channel.basicPublish(EXCHANGE_NAME,ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
        }
        //5 记得关闭相关的连接
        channel.close();
        connection.close();
    }
}

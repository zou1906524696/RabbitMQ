package com.zzf.rabbitmq.directexchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitProducer {
    private static final String EXCHANGE_NAME = "test_direct_exchange";
    private static final String ROUTING_KEY = "test.direct";
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


        //发送一条持久化的消息
        for (int i = 0; i < 10; i++) {
            String message = "Hello RabbitMq Direct Exchange Message";
            channel.basicPublish(EXCHANGE_NAME,ROUTING_KEY, null,message.getBytes());
        }
        //5 记得关闭相关的连接
        channel.close();
        connection.close();
    }
}

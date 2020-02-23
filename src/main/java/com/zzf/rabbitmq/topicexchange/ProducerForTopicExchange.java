package com.zzf.rabbitmq.topicexchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProducerForTopicExchange {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setHost("148.70.22.234");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        String exchangeName= "test_topic_exchange";
        String routeKey1 = "user.save";
        String routeKey2 = "user.update";
        String routeKey3 = "user.delete.abc";

        String message = "hello RabbitMQ topic exchange";
        channel.basicPublish(exchangeName,routeKey1,null,message.getBytes());
        channel.basicPublish(exchangeName,routeKey2,null,message.getBytes());
        channel.basicPublish(exchangeName,routeKey3,null,message.getBytes());

        channel.close();
        connection.close();
    }

}

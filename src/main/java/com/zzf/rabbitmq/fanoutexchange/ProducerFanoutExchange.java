package com.zzf.rabbitmq.fanoutexchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProducerFanoutExchange {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("148.70.22.234");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPassword("guest");


        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        String exchangeName= "test_fanout_exchange";
        String message = "hello world RabbitMQ fanout exchange";
        for (int i = 0; i <10 ; i++) {
            channel.basicPublish(exchangeName,"",null,message.getBytes());
        }
        channel.close();
        connection.close();
    }
}

package com.zzf.rabbitmq.directexchange;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RabbitConsumer {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //1 创建一个connectionFactory
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("148.70.22.234");
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        //这里的连接方式与生产者的demo有些不同，注意辨别区别
        Connection connection = factory.newConnection();

        //3 创建Channel
        Channel channel = connection.createChannel();
        String exchangeName = "test_direct_exchange";
        String exchangeType = "direct";
        String queueName = "test_direct_queue";
        String routingKey = "test.direct";
//        channel.exchangeDeclare(exchangeName,exchangeType,true,false,false,null);
//        channel.queueDeclare(queueName,false,false,false,null);
//        channel.queueBind(queueName,exchangeName,routingKey);

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费端消息： " + new String(body));
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        channel.basicConsume(queueName,consumer);
    }
}

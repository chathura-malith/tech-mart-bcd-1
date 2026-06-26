package com.techmart.ejb;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

import java.nio.charset.StandardCharsets;

@Singleton
@Startup
public class OrderProcessorSessionBean {

    private final static String QUEUE_NAME = "techmart_order_queue";
    private Connection connection;
    private Channel channel;

    @EJB
    private OrderPersisterSessionBean orderPersister;

    @PostConstruct
    public void init() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");

            connection = factory.newConnection();
            channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            System.out.println(" [*] Waiting for messages in " + QUEUE_NAME);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received Order Payload: " + message);

                try {
                    orderPersister.saveOrderToDatabase(message);

                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                    System.out.println(" [x] Order successfully saved and acknowledged.");
                } catch (Exception e) {
                    System.err.println(" [!] Error processing order: " + e.getMessage());
                    channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, false);
                }
            };

            channel.basicConsume(QUEUE_NAME, false, deliverCallback, consumerTag -> { });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void cleanup() {
        try {
            if (channel != null) channel.close();
            if (connection != null) connection.close();
            System.out.println(" [*] RabbitMQ connections closed safely.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
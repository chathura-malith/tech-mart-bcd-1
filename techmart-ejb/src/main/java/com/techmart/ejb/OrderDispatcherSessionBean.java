package com.techmart.ejb;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.techmart.core.dto.message.OrderMessageDto;
import com.techmart.core.service.OrderDispatcherService;
import jakarta.ejb.Stateless;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import java.nio.charset.StandardCharsets;

@Stateless
public class OrderDispatcherSessionBean implements OrderDispatcherService {

    private final static String QUEUE_NAME = "techmart_order_queue";

    @Override
    public void dispatchOrder(OrderMessageDto orderMessage) {

        try (Jsonb jsonb = JsonbBuilder.create()) {
            String jsonMessage = jsonb.toJson(orderMessage);

            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");

            try (Connection connection = factory.newConnection();
                 Channel channel = connection.createChannel()) {

                channel.queueDeclare(QUEUE_NAME, true, false, false, null);
                channel.basicPublish("", QUEUE_NAME, null, jsonMessage.getBytes(StandardCharsets.UTF_8));

                System.out.println(" [x] Successfully dispatched Order to RabbitMQ: " + jsonMessage);

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to dispatch order to RabbitMQ", e);
        }
    }
}
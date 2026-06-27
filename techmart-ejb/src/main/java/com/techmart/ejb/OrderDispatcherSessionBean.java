package com.techmart.ejb;

import com.techmart.core.dto.message.OrderMessageDto;
import com.techmart.core.service.OrderDispatcherService;
import jakarta.annotation.Resource;
import jakarta.ejb.Stateless;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

@Stateless
public class OrderDispatcherSessionBean implements OrderDispatcherService {

    @Resource(lookup = "jms/TechMartFactory")
    private ConnectionFactory connectionFactory;

    @Resource(lookup = "jms/OrderQueue")
    private Queue orderQueue;

    @Override
    public void dispatchOrder(OrderMessageDto orderMessage) {

        try (Jsonb jsonb = JsonbBuilder.create()) {
            String jsonMessage = jsonb.toJson(orderMessage);

            try (JMSContext context = connectionFactory.createContext()) {
                context.createProducer().send(orderQueue, jsonMessage);

                System.out.println(" [x] Successfully dispatched Order to JMS Queue: " + jsonMessage);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to dispatch order to JMS Queue", e);
        }
    }
}
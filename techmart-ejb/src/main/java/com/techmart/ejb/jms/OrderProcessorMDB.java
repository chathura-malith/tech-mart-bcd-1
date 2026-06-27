package com.techmart.ejb.jms;

import com.techmart.ejb.OrderPersisterSessionBean;
import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.EJB;
import jakarta.ejb.MessageDriven;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/OrderQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue")
})
public class OrderProcessorMDB implements MessageListener {

    @EJB
    private OrderPersisterSessionBean orderPersister;

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String jsonPayload = textMessage.getText();

                System.out.println(" [x] MDB Received Order Payload: " + jsonPayload);
                orderPersister.saveOrderToDatabase(jsonPayload);

                System.out.println(" [x] Order successfully processed and saved to Database.");
            } else {
                System.err.println(" [!] Received invalid message type.");
            }
        } catch (Exception e) {
            System.err.println(" [!] Error processing order in MDB: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
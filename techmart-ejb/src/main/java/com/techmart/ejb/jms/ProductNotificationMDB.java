package com.techmart.ejb.jms;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/ProductTopic"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Topic")
})
public class ProductNotificationMDB implements MessageListener {

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                String notificationPayload = ((TextMessage) message).getText();

                System.out.println(" [x] Broadcast MDB Woke up! Received Notification: " + notificationPayload);

                System.out.println(" [x] Fetching 10,000+ subscribed users from database...");
//                Thread.sleep(1500);

                System.out.println(" [x] Sending promotional emails to all users... (Background Task)");
                System.out.println(" [x] Email Broadcast Completed Successfully!");
            }
        } catch (Exception e) {
            System.err.println(" [!] Error in broadcasting product notification: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
package com.techmart.ejb;

import com.techmart.core.service.PaymentAndNotificationService;
import com.techmart.ejb.interceptor.MetricsInterceptor;
import jakarta.ejb.AsyncResult;
import jakarta.ejb.Asynchronous;
import jakarta.ejb.Stateless;
import jakarta.interceptor.Interceptors;

import java.math.BigDecimal;
import java.util.concurrent.Future;

@Stateless
public class PaymentAndNotificationSessionBean implements PaymentAndNotificationService {

    @Override
    @Asynchronous
    @Interceptors(MetricsInterceptor.class)
    public Future<Boolean> processPayment(String cardNumber, BigDecimal amount) {
        System.out.println(" [x] Processing payment of LKR " + amount + " for card ending in: " + cardNumber.substring(Math.max(0, cardNumber.length() - 4)));

        try {
            Thread.sleep(2000);

            boolean isSuccess = !cardNumber.endsWith("0000");

            System.out.println(" [x] Payment Processing Completed. Status: " + (isSuccess ? "SUCCESS" : "FAILED"));

            return new AsyncResult<>(isSuccess);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println(" [!] Payment processing interrupted!");
            return new AsyncResult<>(false);
        }
    }

    @Override
    @Asynchronous
    public void sendConfirmationEmail(String email) {
        System.out.println(" [x] Preparing to send confirmation email to: " + email);

        try {
            Thread.sleep(2000);
            System.out.println(" [x] Email successfully sent to: " + email);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println(" [!] Failed to send email to: " + email);
        }
    }
}
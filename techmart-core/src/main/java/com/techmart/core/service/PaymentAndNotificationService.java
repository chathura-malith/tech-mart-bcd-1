package com.techmart.core.service;

import jakarta.ejb.Remote;

import java.math.BigDecimal;
import java.util.concurrent.Future;

@Remote
public interface PaymentAndNotificationService {

    Future<Boolean> processPayment(String cardNumber, BigDecimal amount);
    void sendConfirmationEmail(String email);
}
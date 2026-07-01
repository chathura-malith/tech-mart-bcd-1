package com.techmart.core.service;

import jakarta.ejb.Remote;

@Remote
public interface SystemMetricsService {
    void recordExecutionTime(long timeInMillis);
    void incrementActiveUsers();
    void decrementActiveUsers();
    void recordSale(int quantity);
    double getAverageResponseTime();
    int getActiveUsers();
    int getItemsSold();
    long getTotalRequests();
}
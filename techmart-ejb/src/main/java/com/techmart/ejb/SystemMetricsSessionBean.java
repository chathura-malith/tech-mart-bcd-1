package com.techmart.ejb;

import com.techmart.core.service.SystemMetricsService;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.ConcurrencyManagement;
import jakarta.ejb.ConcurrencyManagementType;
import jakarta.ejb.Lock;
import jakarta.ejb.LockType;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

@Singleton
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class SystemMetricsSessionBean implements SystemMetricsService {

    private long totalExecutionTime = 0;
    private long totalRequests = 0;
    private int activeUsers = 0;
    private int itemsSold = 0;

    @PostConstruct
    public void init() {
        System.out.println(" [x] SystemMetricsEJB (Singleton) Initialized. Performance tracking started.");
    }


    @Lock(LockType.WRITE)
    public void recordExecutionTime(long timeInMillis) {
        this.totalExecutionTime += timeInMillis;
        this.totalRequests++;
    }

    @Lock(LockType.WRITE)
    public void incrementActiveUsers() {
        this.activeUsers++;
    }

    @Lock(LockType.WRITE)
    public void decrementActiveUsers() {
        if (this.activeUsers > 0) this.activeUsers--;
    }

    @Lock(LockType.WRITE)
    public void recordSale(int quantity) {
        this.itemsSold += quantity;
    }

    @Lock(LockType.READ)
    public double getAverageResponseTime() {
        return totalRequests == 0 ? 0.0 : (double) totalExecutionTime / totalRequests;
    }

    @Lock(LockType.READ)
    public int getActiveUsers() {
        return activeUsers;
    }

    @Lock(LockType.READ)
    public int getItemsSold() {
        return itemsSold;
    }

    @Lock(LockType.READ)
    public long getTotalRequests() {
        return totalRequests;
    }
}
package com.techmart.ejb.interceptor;

import com.techmart.core.service.SystemMetricsService;
import jakarta.ejb.EJB;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.InvocationContext;

public class MetricsInterceptor {

    @EJB
    private SystemMetricsService metricsBean;

    @AroundInvoke
    public Object measureExecutionTime(InvocationContext context) throws Exception {

        long startTime = System.currentTimeMillis();

        try {
            return context.proceed();

        } finally {
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;

            if (metricsBean != null) {
                metricsBean.recordExecutionTime(executionTime);
            }

            System.out.println(" [\u23F1\uFE0F Metrics] " + context.getTarget().getClass().getSimpleName() +
                    "." + context.getMethod().getName() + " executed in " + executionTime + "ms");
        }
    }
}
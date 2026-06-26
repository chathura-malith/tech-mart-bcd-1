package com.techmart.core.service;

import com.techmart.core.dto.message.OrderMessageDto;
import jakarta.ejb.Remote;

@Remote
public interface OrderDispatcherService {
    void dispatchOrder(OrderMessageDto orderMessage);
}
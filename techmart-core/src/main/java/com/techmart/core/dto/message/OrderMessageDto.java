package com.techmart.core.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderMessageDto implements Serializable {

    private Integer userId;
    private Integer addressId;
    private BigDecimal totalAmount;
    private List<OrderItemMessageDto> items;
}
package com.techmart.core.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemMessageDto implements Serializable {
    private Integer productId;
    private Integer quantity;
    private BigDecimal unitPrice;
}
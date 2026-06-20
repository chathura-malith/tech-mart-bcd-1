package com.techmart.core.dto.response;

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
public class ProductResponseDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private BigDecimal price;
    private Integer stockQuantity;
    private String imageUrl;

    private Integer categoryId;
    private String categoryName;
}
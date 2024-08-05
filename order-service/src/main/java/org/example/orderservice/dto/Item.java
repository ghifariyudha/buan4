package org.example.orderservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Item {

    private Long productId;
    private String sku;
    private BigDecimal price;
    private Integer quantity;
}
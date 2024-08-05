package org.example.inventoryservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class InventoryRequest {

    private Long productId;
    private String sku;
    private BigDecimal price;
    private Integer quantity;
}
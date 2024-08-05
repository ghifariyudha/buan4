package org.example.inventoryservice;

import org.example.inventoryservice.domain.Inventory;
import org.example.inventoryservice.dto.InventoryRequest;
import org.example.inventoryservice.repository.InventoryRepository;
import org.example.inventoryservice.service.InventoryService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InventoryServiceApplicationTests {

    @InjectMocks private InventoryService inventoryService;
    @Mock private InventoryRepository inventoryRepository;

    @Test
    @Order(1)
    public void createInventory() {
        var inventory = new Inventory();
        inventory.setProductId(1L);
        inventory.setSku("productA-sku1");
        inventory.setPrice(BigDecimal.valueOf(1_000));
        inventory.setQuantity(10);
        when(inventoryRepository.save(any(Inventory.class))).thenReturn(inventory);

        var request = new InventoryRequest();
        request.setProductId(1L);
        request.setSku("productA-sku1");
        request.setPrice(BigDecimal.valueOf(1_000));
        request.setQuantity(10);
        var create = inventoryService.create(request);
        assertNotNull(create);
        assertEquals(1, create.getProductId());
        assertEquals("productA-sku1", create.getSku());
        assertEquals(BigDecimal.valueOf(1_000), create.getPrice());
        assertEquals(10, create.getQuantity());

        verify(inventoryRepository, times(1)).save(any(Inventory.class));
    }
}
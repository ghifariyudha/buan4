package org.example.inventoryservice.repository;

import org.example.inventoryservice.domain.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findByProductIdAndSkuIn(Long productId, List<String> skus);
}
package org.example.inventoryservice.service;

import org.example.inventoryservice.domain.Inventory;
import org.example.inventoryservice.dto.InventoryRequest;
import org.example.inventoryservice.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InventoryService {

    @Autowired
    InventoryRepository inventoryRepository;

    public Inventory create(InventoryRequest req) {
        var inventory = new Inventory();
        inventory.setProductId(req.getProductId());
        inventory.setSku(req.getSku() != null ? req.getSku() : UUID.randomUUID().toString());
        inventory.setPrice(req.getPrice());
        inventory.setQuantity(req.getQuantity());
        return inventoryRepository.save(inventory);
    }

    public List<Inventory> checkStock(List<InventoryRequest> req) {
        var productSkuMap = new HashMap<Long, List<String>>();
        for (InventoryRequest request : req) {
            productSkuMap.computeIfAbsent(request.getProductId(), k -> new ArrayList<>()).add(request.getSku());
        }

        var result = new ArrayList<Inventory>();
        for (Map.Entry<Long, List<String>> entry : productSkuMap.entrySet()) {
            var queryInventories = inventoryRepository.findByProductIdAndSkuIn(entry.getKey(), entry.getValue());
            result.addAll(queryInventories);
        }
        return result;
    }

    public void updateStock(List<InventoryRequest> req) {
        var productSkuMap = new HashMap<Long, List<String>>();
        for (InventoryRequest request : req) {
            productSkuMap.computeIfAbsent(request.getProductId(), k -> new ArrayList<>()).add(request.getSku());
        }

        var result = new ArrayList<Inventory>();
        for (Map.Entry<Long, List<String>> entry : productSkuMap.entrySet()) {
            var queryInventories = inventoryRepository.findByProductIdAndSkuIn(entry.getKey(), entry.getValue());
            for (Inventory inventory : queryInventories) {
                for (InventoryRequest request : req) {
                    if (inventory.getProductId().equals(request.getProductId()) && inventory.getSku().equals(request.getSku())) {
                        inventory.setQuantity(inventory.getQuantity() - request.getQuantity());
                        break;
                    }
                }
            }
            result.addAll(queryInventories);
        }
        inventoryRepository.saveAll(result);
    }
}
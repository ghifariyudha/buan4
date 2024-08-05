package org.example.inventoryservice.controller;

import org.example.inventoryservice.dto.InventoryRequest;
import org.example.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("inventory")
public class InventoryController {

    @Autowired InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody InventoryRequest req) {
        return ResponseEntity.ok(inventoryService.create(req));
    }

    @PostMapping("check-stock")
    public ResponseEntity<Object> checkStock(@RequestBody List<InventoryRequest> req) {
        return ResponseEntity.ok(inventoryService.checkStock(req));
    }

    @PostMapping("update-stock")
    public ResponseEntity<Object> updateStock(@RequestBody List<InventoryRequest> req) {
        inventoryService.updateStock(req);
        return ResponseEntity.ok().build();
    }
}
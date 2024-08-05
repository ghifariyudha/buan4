package org.example.orderservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.orderservice.dto.Item;
import org.example.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired OrderService orderService;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody List<Item> req) throws JsonProcessingException {
        return ResponseEntity.ok(orderService.create(req));
    }
}
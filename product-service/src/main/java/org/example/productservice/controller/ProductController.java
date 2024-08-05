package org.example.productservice.controller;

import org.example.productservice.dto.ProductRequest;
import org.example.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired ProductService productService;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody ProductRequest req) {
        return ResponseEntity.ok(productService.create(req));
    }

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }
}
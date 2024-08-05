package org.example.productservice.service;

import org.example.productservice.domain.Product;
import org.example.productservice.dto.ProductRequest;
import org.example.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired ProductRepository productRepository;

    public Product create(ProductRequest req) {
        var product = new Product();
        product.setName(req.getName());
        product.setDesc(req.getDesc());
        return productRepository.save(product);
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }
}
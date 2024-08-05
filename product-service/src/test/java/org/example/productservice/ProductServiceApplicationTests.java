package org.example.productservice;

import org.example.productservice.domain.Product;
import org.example.productservice.dto.ProductRequest;
import org.example.productservice.repository.ProductRepository;
import org.example.productservice.service.ProductService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductServiceApplicationTests {

    @InjectMocks private ProductService productService;
    @Mock private ProductRepository productRepository;

    @Test
    @Order(1)
    public void createProduct() {
        var product = new Product();
        product.setName("Product A");
        product.setDesc("Description A");
        when(productRepository.save(any(Product.class))).thenReturn(product);

        var request = new ProductRequest();
        request.setName("Product A");
        request.setDesc("Description A");
        var create = productService.create(request);
        assertNotNull(create);
        assertEquals("Product A", create.getName());
        assertEquals("Description A", create.getDesc());

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    @Order(2)
    public void getAllProduct() {
        var products = new ArrayList<Product>();
        var productA = new Product();
        productA.setName("Product A");
        productA.setDesc("Description A");
        products.add(productA);
        var productB = new Product();
        productB.setName("Product B");
        productB.setDesc("Description B");
        products.add(productB);
        when(productRepository.findAll()).thenReturn(products);

        var getAll = productService.getAll();
        assertNotNull(getAll);
        assertEquals(2, getAll.size());
        assertEquals("Product A", getAll.get(0).getName());
        assertEquals("Description A", getAll.get(0).getDesc());
    }
}
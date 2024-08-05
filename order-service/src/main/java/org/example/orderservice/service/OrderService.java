package org.example.orderservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.example.orderservice.domain.Order;
import org.example.orderservice.dto.Item;
import org.example.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired OrderRepository orderRepository;
    @Autowired RestTemplate restTemplate;
    @Autowired ObjectMapper objectMapper;
    @Autowired RedisTemplate<String, String> redisTemplate;

    @Transactional
    public Order create(List<Item> req) throws JsonProcessingException {
        var itemMap = req.stream().collect(Collectors.toMap(m -> m.getProductId() + ":" + m.getSku(), Function.identity()));
        var order = new Order();
        var redisKeys = new ArrayList<String>();
        var checkStock = restTemplate.postForEntity("http://localhost:8000/api-gateway/inventory/check-stock", req, String.class);
        if (checkStock.getStatusCode().is2xxSuccessful() && checkStock.hasBody()) {
            var responseBody = objectMapper.readValue(checkStock.getBody(), new TypeReference<List<Item>>() {});
            try {
                for (Item item : responseBody) {
                    var itm = itemMap.get(item.getProductId() + ":" + item.getSku());
                    if (itm != null) {
                        if (item.getQuantity() == 0 || item.getQuantity() < itm.getQuantity()) {
                            throw new RuntimeException(String.format("Product %s SKU %s is not in stock", item.getProductId(), item.getSku()));
                        }
                        itm.setPrice(item.getPrice());
                    }
                    var redisKey = "inventory-lock:" + item.getProductId() + ":" + item.getSku();
                    var setKey = redisTemplate.opsForValue().setIfAbsent(redisKey, "locked", 1, TimeUnit.MINUTES);
                    if (setKey == null || !setKey) {
                        throw new RuntimeException(String.format("Unable to set lock for product %s SKU %s", item.getProductId(), item.getSku()));
                    }
                    redisKeys.add(redisKey);
                }
                order.setOrderNumber(String.valueOf(System.nanoTime()));
                order.setItems(List.copyOf(itemMap.values()));
                orderRepository.save(order);

                restTemplate.postForEntity("http://localhost:8000/api-gateway/inventory/update-stock", req, String.class);
            } finally {
                redisTemplate.delete(redisKeys);
            }
        }
        return order;
    }
}
package org.example.gatewayservice;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    private static final String PATH_DEFAULT = "/api-gateway/";

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                //PRODUCT-SERVICE
                .route("productRoute",
                        r -> r.path(PATH_DEFAULT + "product/**")
                                .filters(f -> f
                                        .rewritePath(PATH_DEFAULT + "product", "/product"))
                                .uri("lb://product-service:8081"))

                //INVENTORY-SERVICE
                .route("inventoryRoute",
                        r -> r.path(PATH_DEFAULT + "inventory/**")
                                .filters(f -> f
                                        .rewritePath(PATH_DEFAULT + "inventory", "/inventory"))
                                .uri("lb://inventory-service:8082"))

                //ORDER-SERVICE
                .route("orderRoute",
                        r -> r.path(PATH_DEFAULT + "order/**")
                                .filters(f -> f
                                        .rewritePath(PATH_DEFAULT + "order", "/order"))
                                .uri("lb://order-service:8083"))
                .build();
    }
}
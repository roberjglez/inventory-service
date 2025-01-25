package com.rjglez.inventory.service;

import com.rjglez.inventory.controller.response.InventoryResponse;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class InventoryService {

    private final Map<UUID, Integer> inventory = new HashMap<>();

    public InventoryResponse getInventory(UUID productId) {
        int quantity = inventory.getOrDefault(productId, 0);
        return InventoryResponse.builder()
                .productId(productId)
                .quantity(quantity)
                .build();
    }

    public InventoryResponse updateInventory(UUID productId, int quantity) {
        inventory.put(productId, quantity);
        return InventoryResponse.builder()
                .productId(productId)
                .quantity(quantity)
                .build();
    }
}


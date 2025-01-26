package com.rjglez.inventory.service;

import com.rjglez.inventory.controller.response.InventoryResponse;
import com.rjglez.inventory.model.InventoryProduct;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class InventoryService {

    private final Map<UUID, InventoryProduct> inventory;
    private final String insufficientStockWarning;

    InventoryService() {
        this.insufficientStockWarning = System.getenv("INSUFFICIENT_STOCK_WARNING");
        UUID firstInventoryProductId = UUID.fromString("8ef0a5c8-10b8-497b-83bb-2d012f6b3d03");
        UUID secondInventoryProductId = UUID.fromString("706ba114-a11e-440a-aa28-d2e68a1b7561");
        UUID thirdInventoryProductId = UUID.fromString("063ded62-99b7-4323-ab17-ec5933691c7c");
        inventory = new ConcurrentHashMap<>();
        inventory.put(firstInventoryProductId, InventoryProduct.builder()
                .id(firstInventoryProductId)
                .name("First product")
                .quantity(35)
                .build());
        inventory.put(secondInventoryProductId, InventoryProduct.builder()
                .id(secondInventoryProductId)
                .name("Second product")
                .quantity(71)
                .build());
        inventory.put(thirdInventoryProductId, InventoryProduct.builder()
                .id(thirdInventoryProductId)
                .name("Third product")
                .quantity(2)
                .build());
    }

    public InventoryResponse getStock(UUID productId) {
        InventoryProduct product = inventory.get(productId);
        return Objects.isNull(product) ? null : InventoryResponse.builder()
                .productId(product.id())
                .quantity(product.quantity())
                .build();
    }

    public InventoryResponse reduceStock(UUID productId, int quantity) {
        InventoryProduct product = inventory.get(productId);

        if (Objects.isNull(product)) {
            return null;
        }

        int newStock = product.quantity() - quantity;
        if (newStock < 0) {
            System.out.println(insufficientStockWarning.replace("{productId}", productId.toString()));
            throw new IllegalArgumentException("Insufficient stock for product: " + productId);
        }

        inventory.put(productId, InventoryProduct.builder()
                .id(productId)
                .name(product.name())
                .quantity(newStock)
                .build());
        return InventoryResponse.builder()
                .productId(productId)
                .quantity(newStock)
                .build();
    }
}


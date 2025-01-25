package com.rjglez.inventory.service;

import com.rjglez.inventory.controller.response.InventoryResponse;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class InventoryServiceTest {

    private final InventoryService inventoryService = new InventoryService();

    @Test
    void getStockWhenProductIdExists() {
        // GIVEN
        UUID productId = UUID.fromString("063ded62-99b7-4323-ab17-ec5933691c7c");
        int quantity = 2;

        // WHEN
        InventoryResponse response = inventoryService.getStock(productId);

        // THEN
        assertThat(response).isNotNull();
        assertThat(response.productId()).isEqualTo(productId);
        assertThat(response.quantity()).isEqualTo(quantity);
    }

    @Test
    void getStockWhenProductIdDoesNotExist() {
        // GIVEN
        UUID productId = UUID.randomUUID();

        // WHEN
        InventoryResponse response = inventoryService.getStock(productId);

        // THEN
        assertThat(response).isNull();
    }

    @Test
    void updateInventoryWhenProductExists() {
        // GIVEN
        UUID productId = UUID.fromString("706ba114-a11e-440a-aa28-d2e68a1b7561");
        int quantity = 41;

        // WHEN
        InventoryResponse response = inventoryService.updateInventory(productId, quantity);

        // THEN
        assertThat(response).isNotNull();
        assertThat(response.productId()).isEqualTo(productId);
        assertThat(response.quantity()).isEqualTo(quantity);
    }
}

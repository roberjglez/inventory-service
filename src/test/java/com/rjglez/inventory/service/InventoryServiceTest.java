package com.rjglez.inventory.service;

import com.rjglez.inventory.controller.response.InventoryResponse;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void reduceStockWhenProductExists() {
        // GIVEN
        UUID productId = UUID.fromString("706ba114-a11e-440a-aa28-d2e68a1b7561");
        int quantity = 4;

        // WHEN
        InventoryResponse response = inventoryService.reduceStock(productId, quantity);

        // THEN
        assertThat(response).isNotNull();
        assertThat(response.productId()).isEqualTo(productId);
        assertThat(response.quantity()).isEqualTo(67);
    }

    @Test
    void reduceStockWhenProductDoesNotExist() {
        // GIVEN
        UUID productId = UUID.randomUUID();
        int quantity = 4;

        // WHEN
        InventoryResponse response = inventoryService.reduceStock(productId, quantity);

        // THEN
        assertThat(response).isNull();
    }

    @Test
    void reduceStockWhenThereIsNoSufficientStock() {
        // GIVEN
        UUID productId = UUID.fromString("063ded62-99b7-4323-ab17-ec5933691c7c");
        int quantity = 4;

        // WHEN
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> inventoryService.reduceStock(productId, quantity)
        );

        // THEN
        assertThat(exception.getMessage()).isEqualTo("Insufficient stock for product: " + productId);
    }
}

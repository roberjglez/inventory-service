package com.rjglez.inventory.controller;

import com.rjglez.inventory.controller.response.InventoryResponse;
import com.rjglez.inventory.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class InventoryControllerTest {

    @Mock
    private InventoryService inventoryService;

    @InjectMocks
    private InventoryController inventoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void checkStockWhenProductIdDoesNotExist() {
        // GIVEN
        String productId = UUID.randomUUID().toString();

        when(inventoryService.getStock(productId)).thenReturn(null);

        // WHEN
        ResponseEntity<InventoryResponse> response = inventoryController.checkStock(productId);

        // THEN
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(404));
        verify(inventoryService, times(1)).getStock(productId);
    }

    @Test
    void checkStockWhenProductIdExists() {
        // GIVEN
        String productId = UUID.randomUUID().toString();

        InventoryResponse inventoryResponse = InventoryResponse.builder()
                .productId(UUID.fromString(productId))
                .quantity(25)
                .build();

        when(inventoryService.getStock(productId)).thenReturn(inventoryResponse);

        // WHEN
        ResponseEntity<InventoryResponse> response = inventoryController.checkStock(productId);

        // THEN
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(response.getBody()).isEqualTo(inventoryResponse);
        verify(inventoryService, times(1)).getStock(productId);
    }

    @Test
    void updateInventoryWhenProductIdExists() {
        // GIVEN
        String productId = UUID.randomUUID().toString();
        int quantity = 35;

        InventoryResponse inventoryResponse = InventoryResponse.builder()
                .productId(UUID.fromString(productId))
                .quantity(quantity)
                .build();

        when(inventoryService.reduceStock(productId, quantity)).thenReturn(inventoryResponse);

        // WHEN
        ResponseEntity<InventoryResponse> response = inventoryController.reduceStock(productId, quantity);

        // THEN
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(response.getBody()).isEqualTo(inventoryResponse);
        verify(inventoryService, times(1)).reduceStock(productId, quantity);
    }

    @Test
    void updateInventoryWhenProductIdDoesNotExist() {
        // GIVEN
        String productId = UUID.randomUUID().toString();
        int quantity = 35;

        when(inventoryService.reduceStock(productId, quantity)).thenReturn(null);

        // WHEN
        ResponseEntity<InventoryResponse> response = inventoryController.reduceStock(productId, quantity);

        // THEN
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(404));
        verify(inventoryService, times(1)).reduceStock(productId, quantity);
    }
}

package com.rjglez.inventory.controller;

import com.rjglez.inventory.controller.response.InventoryResponse;
import com.rjglez.inventory.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/inventory")
@Tag(name = "Inventory Service", description = "Endpoints for managing inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @Operation(description = "Returns the available stock for a given product ID")
    @GetMapping("/check/{productId}")
    public ResponseEntity<InventoryResponse> checkStock(@PathVariable UUID productId) {
        InventoryResponse response = inventoryService.getStock(productId);

        if (Objects.isNull(response)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }

    @Operation(description = "Reduces the stock for a given product ID and quantity")
    @PutMapping(value = "/reduce/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InventoryResponse> reduceStock(@PathVariable UUID productId, @RequestParam int quantity) {
        InventoryResponse response = inventoryService.reduceStock(productId, quantity);
        if (Objects.isNull(response)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }
}

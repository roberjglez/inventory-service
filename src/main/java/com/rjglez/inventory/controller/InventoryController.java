package com.rjglez.inventory.controller;

import com.rjglez.inventory.controller.response.InventoryResponse;
import com.rjglez.inventory.service.InventoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/check/{productId}")
    public ResponseEntity<InventoryResponse> checkStock(@PathVariable UUID productId) {
        InventoryResponse response = inventoryService.getStock(productId);

        if (Objects.isNull(response)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InventoryResponse> updateInventory(@PathVariable UUID productId, @RequestBody int quantity) {
        InventoryResponse response = inventoryService.updateInventory(productId, quantity);
        if (Objects.isNull(response)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }
}

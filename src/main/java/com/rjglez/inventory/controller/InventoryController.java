package com.rjglez.inventory.controller;

import com.rjglez.inventory.controller.response.InventoryResponse;
import com.rjglez.inventory.service.InventoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping(value = "/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InventoryResponse> getInventory(@PathVariable UUID productId) {
        return ResponseEntity.ok(inventoryService.getInventory(productId));
    }

    @PutMapping(value = "/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InventoryResponse> updateInventory(@PathVariable UUID productId, @RequestBody int quantity) {
        return ResponseEntity.ok(inventoryService.updateInventory(productId, quantity));
    }
}

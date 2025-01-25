package com.rjglez.inventory.model;

import lombok.Builder;

import java.util.UUID;

@Builder
public record InventoryProduct(UUID id, String name, int quantity) {
}

package com.rjglez.inventory.controller.response;

import lombok.Builder;

import java.util.UUID;

@Builder
public record InventoryResponse(UUID productId, int quantity) {
}

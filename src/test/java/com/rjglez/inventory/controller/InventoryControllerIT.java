package com.rjglez.inventory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class InventoryControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void checkStockWhenProductIdDoesNotExist() throws Exception {
        // GIVEN
        UUID productId = UUID.randomUUID();

        // THEN
        mockMvc.perform(get("/inventory/check/{productId}", productId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void checkStockWhenProductIdExists() throws Exception {
        // GIVEN
        UUID productId = UUID.fromString("706ba114-a11e-440a-aa28-d2e68a1b7561");

        // THEN
        mockMvc.perform(get("/inventory/check/{productId}", productId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(productId.toString()))
                .andExpect(jsonPath("$.quantity").value(71));
    }

    @Test
    void updateInventoryWhenItExists() throws Exception {
        // GIVEN
        UUID productId = UUID.fromString("8ef0a5c8-10b8-497b-83bb-2d012f6b3d03");
        int quantity = 26;

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String updateInventoryRequestJson = ow.writeValueAsString(quantity);

        // THEN
        mockMvc.perform(put("/inventory/{productId}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateInventoryRequestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(productId.toString()))
                .andExpect(jsonPath("$.quantity").value(quantity));
    }

    @Test
    void updateInventoryWhenItDoesNotExist() throws Exception {
        // GIVEN
        UUID productId = UUID.randomUUID();
        int quantity = 26;

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String updateInventoryRequestJson = ow.writeValueAsString(quantity);

        // THEN
        mockMvc.perform(put("/inventory/{productId}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateInventoryRequestJson))
                .andExpect(status().isNotFound());
    }
}

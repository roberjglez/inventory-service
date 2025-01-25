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
    void getInventoryWhenThereIsNotOrProductIdDoesNotExist() throws Exception {
        // GIVEN
        UUID productId = UUID.randomUUID();

        // THEN
        mockMvc.perform(get("/inventory/{productId}", productId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(productId.toString()))
                .andExpect(jsonPath("$.quantity").value(0));
    }

    @Test
    void updateInventory() throws Exception {
        // GIVEN
        UUID productId = UUID.randomUUID();
        int quantity = 1;

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


}

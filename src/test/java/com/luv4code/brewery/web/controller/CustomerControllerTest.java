package com.luv4code.brewery.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv4code.brewery.services.CustomerService;
import com.luv4code.brewery.web.model.CustomerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @MockBean
    CustomerService customerService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    CustomerDto validCustomer;

    @BeforeEach
    void setUp() throws Exception {
        validCustomer = CustomerDto.builder()
                .id(UUID.randomUUID())
                .name("Madhav")
                .build();
    }

    @Test
    public void getCustomer() throws Exception {
        given(customerService.getCustomerById(any(UUID.class))).willReturn(validCustomer);

        mockMvc.perform(get("/api/v1/customer/" + validCustomer.getId().toString()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(validCustomer.getId().toString())))
                .andExpect(jsonPath("$.name", is("Madhav")));
    }

    @Test
    void handlePost() throws Exception {
        CustomerDto customerDto = this.validCustomer;
        customerDto.setId(null);
        CustomerDto savedCustomer = CustomerDto.builder().id(UUID.randomUUID()).name("New Customer").build();
        String customerDtoJson = objectMapper.writeValueAsString(customerDto);

        given(customerService.saveCustomer(any())).willReturn(savedCustomer);

        mockMvc.perform(post("/api/v1/customer/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerDtoJson))
                .andExpect(status().isCreated());
    }

    @Test
    void handleUpdate() throws Exception {
        CustomerDto customerDto = this.validCustomer;
        String customerDtoJson = objectMapper.writeValueAsString(customerDto);

        mockMvc.perform(put("/api/v1/customer/" + validCustomer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerDtoJson))
                .andExpect(status().isNoContent());
        then(customerService).should().updateCustomer(any(), any());
    }
}
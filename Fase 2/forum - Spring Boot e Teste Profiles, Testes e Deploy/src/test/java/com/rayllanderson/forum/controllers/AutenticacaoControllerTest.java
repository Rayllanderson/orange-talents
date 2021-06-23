package com.rayllanderson.forum.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class AutenticacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deveriaDevolver400CasoDadosDeAutenticacaoEstejamIncorretos() throws Exception {
        URI uri = new URI("/auth");
        String json = "{\"email\": \"invalid@email.com\", \"senha\": \"12233\"}";

        mockMvc.perform(MockMvcRequestBuilders
                     .post(uri)
                     .content(json)
                     .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(400));
    }
}
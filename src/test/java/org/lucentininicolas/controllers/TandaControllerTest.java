package org.lucentininicolas.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.lucentininicolas.ApplicationConfig;
import org.lucentininicolas.dtos.TandaDto;
import org.lucentininicolas.services.TandaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(ApplicationConfig.class)
public class TandaControllerTest {
    private static final String TANDA_PATH = "/tanda";
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TandaService tandaService;

    @Test
    void shouldFetchAllTandas() throws Exception {
        List<TandaDto> tandas = new ArrayList<>();
        tandas.add(TandaDto.builder().id(1).build());
        when(tandaService.getAll()).thenReturn(tandas);
        this.mockMvc.perform(get(TANDA_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", Is.is(1)));
    }
    @Test
    void shouldCreateATanda() throws Exception {

        TandaDto tanda = TandaDto.builder().id(1).build();
        ObjectMapper objectMapper = new ObjectMapper();
        String battleJson = objectMapper.writeValueAsString(tanda);

        when(tandaService.create(any(TandaDto.class))).thenReturn(tanda);

        this.mockMvc.perform(post(TANDA_PATH)
                        .contentType("application/json")
                        .content(battleJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Is.is(1)));
    }
}

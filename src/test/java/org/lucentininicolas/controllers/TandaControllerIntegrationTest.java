package org.lucentininicolas.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.lucentininicolas.ApplicationConfig;
import org.lucentininicolas.dtos.TandaDto;
import org.lucentininicolas.services.TandaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureDataMongo
@Import(ApplicationConfig.class)
public class TandaControllerIntegrationTest {
    private static final String TANDA_PATH = "/tanda";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TandaService tandaService;

    @Autowired
    private ObjectMapper objectMapper;
    @Test
    void shouldReturnBadRequestOnExistingIdIntegration() throws Exception {

        TandaDto tanda = TandaDto.builder().id(100).build();

        ObjectMapper objectMapper = new ObjectMapper();
        String battleJson = objectMapper.writeValueAsString(tanda);

        tandaService.create(tanda);

        this.mockMvc.perform(post(TANDA_PATH)
                        .contentType("application/json")
                        .content(battleJson))
                .andExpect(status().isBadRequest());
    }
    @Test
    void shouldReturnTandaBetweenWithSearch() throws Exception {

        TandaDto tanda3 = TandaDto.builder().id(3).creationDate(LocalDate.of(2025,10,10)).temperature(20).build();
        TandaDto tanda1 = TandaDto.builder().id(1).creationDate(LocalDate.of(2025,2,10)).temperature(10).build();
        TandaDto tanda2 = TandaDto.builder().id(2).creationDate(LocalDate.of(2025,5,10)).temperature(25).build();

        tandaService.create(tanda1);
        tandaService.create(tanda2);
        tandaService.create(tanda3);


        this.mockMvc.perform(get(TANDA_PATH+"/search")
                        .param("startDate", "2024-01-01")
                        .param("endDate", "2025-06-28"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json(objectMapper.writeValueAsString(List.of(tanda1, tanda2))));

    }
}

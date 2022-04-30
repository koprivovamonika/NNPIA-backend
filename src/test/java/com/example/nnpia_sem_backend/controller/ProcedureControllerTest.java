package com.example.nnpia_sem_backend.controller;

import com.example.nnpia_sem_backend.dto.ProcedureDto;
import com.example.nnpia_sem_backend.entity.BeautyProcedure;
import com.example.nnpia_sem_backend.repository.ProcedureRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProcedureControllerTest {

    public static final String API_PROCEDURE = "/api/procedure";

    @Autowired
    ProcedureRepository procedureRepository;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @AfterAll
    public void cleanUp() {
        BeautyProcedure testProcedure = procedureRepository.findByName("testprocedure");
        procedureRepository.deleteById(testProcedure.getId());
    }


    @Test
    @Order(1)
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void createProcedure() throws Exception {
        ProcedureDto procedureDto = new ProcedureDto();
        procedureDto.setName("testprocedure");
        procedureDto.setPrice(250.0);
        procedureDto.setDescription("testovaci procedura");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);

        mockMvc.perform(post(API_PROCEDURE).contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(procedureDto)))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void updateProcedure() throws Exception {
        BeautyProcedure testProcedure = procedureRepository.findByName("testprocedure");
        ProcedureDto procedureDto = new ProcedureDto();
        procedureDto.setName("testprocedure");
        procedureDto.setPrice(400.0);
        procedureDto.setDescription("testovaci procedura update");
        procedureDto.setId(testProcedure.getId());
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);

        mockMvc.perform(put(API_PROCEDURE).contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(procedureDto)))
                .andExpect(status().isOk());

    }

}
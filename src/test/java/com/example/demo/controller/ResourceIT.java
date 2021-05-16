package com.example.demo.controller;


import com.example.demo.WebLayerConfig;
import com.example.demo.model.*;
import com.example.demo.service.CaseService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = WebLayerConfig.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ResourceIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CaseService caseService;

    static private String listResponse;

    @BeforeAll
    public static void init() throws IOException {
        //todo fix to get from resources
        listResponse = String.join("", Files.readAllLines(Paths.get("G:\\Java\\demo\\src\\test\\resources\\responses\\caseList.json")));
    }

    @Test
    public void shouldReturnCaseList() throws Exception {
        Case caseModel = createCase();
        when(caseService.getCasesByStatus(Case.Status.OPEN)).thenReturn(Collections.singletonList(caseModel));

        MvcResult result = this.mockMvc.perform(get("/cases/status/OPEN"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        JSONAssert.assertEquals(listResponse, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void shouldReturn400WhenInvalidStatus() throws Exception {
       this.mockMvc.perform(get("/cases/status/INPROGRESS"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    private Case createCase(){
        return CaseBuilder.aCase()
                .caseId(13)
                .description("desc")
                .notes(Collections.singletonList(createNote()))
                .severity(1)
                .title("title")
                .user(createUser())
                .status(Case.Status.OPEN)
                .build();
    }

    private Note createNote(){
        return NoteBuilder.aNote().noteId(14).details("note").caseId(13).build();
    }

    private User createUser(){
        return UserBuilder.anUser().userId(15).email("email").firstName("firstName").lastName("lastName").build();
    }
}

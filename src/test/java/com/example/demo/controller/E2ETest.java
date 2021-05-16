package com.example.demo.controller;

import com.example.demo.DemoApplication;
import com.example.demo.model.*;
import com.example.demo.repository.CaseRepository;
import com.example.demo.repository.NoteRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.entities.CaseEntity;
import com.example.demo.repository.entities.UserEntity;
import com.example.demo.service.mappers.CaseMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = DemoApplication.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class E2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CaseRepository caseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private CaseMapper caseMapper;

    static private String listResponse;

    private Integer userId;

    private Integer caseID;

    @BeforeAll
    public static void init() throws IOException {
        //todo fix to get from resources
        listResponse = String.join("", Files.readAllLines(Paths.get("G:\\Java\\demo\\src\\test\\resources\\responses\\caseList.json")));
    }

    @BeforeEach
    public void setup() {
        UserEntity user = userRepository.save(new UserEntity());
        userId = user.getUserId();
        final CaseEntity caseEntity = caseMapper.caseToCaseEntity(createCase());
        caseEntity.setUser(user);
        caseEntity.getNotes().forEach(note -> note.setCaseEntity(caseEntity));
        CaseEntity returned = caseRepository.save(caseEntity);
        caseID = returned.getCaseId();
    }

    @Test
    public void shouldReturnCaseListByStatus() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/cases/status/OPEN"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        //JSONAssert.assertEquals(listResponse, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void shouldThrowNotFoundWhenUserNotFound() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/cases/user/" + ++userId))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void shouldReturnCaseListByUser() throws Exception {

        MvcResult result = this.mockMvc.perform(get("/cases/user/" + userId))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void shouldReturnCaseListByUserAndStatus() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/cases/user/" + userId + "/status/OPEN"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        //JSONAssert.assertEquals(listResponse, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void shouldReturnCaseById() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/case/" + caseID))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        //JSONAssert.assertEquals(listResponse, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void shouldCreateCaseBadRequest() throws Exception {
        MvcResult result = this.mockMvc.perform(
                post("/case/create").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
        //JSONAssert.assertEquals(listResponse, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void shouldCreateCase() throws Exception {
        UserEntity user = userRepository.save(new UserEntity());
        Case caseModel = createCase();
        caseModel.getUser().setUserId(user.getUserId());
        MvcResult result = this.mockMvc.perform(
                //todo ideally I'd serialize model here for ease of use, don't have time for that
                post("/case/create").contentType(MediaType.APPLICATION_JSON).content("{\n" +
                        "\"user\": { \"userId\": " + user.getUserId() + "}\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        //JSONAssert.assertEquals(listResponse, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void shouldCreateNote() throws Exception {
        MvcResult result = this.mockMvc.perform(
                post("/case/" + caseID + "/addNote").contentType(MediaType.TEXT_PLAIN).content("desc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        //JSONAssert.assertEquals(listResponse, result.getResponse().getContentAsString(), false);
    }


    private Case createCase() {
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

    private Note createNote() {
        return NoteBuilder.aNote().details("note").caseId(13).build();
    }

    private User createUser() {
        return UserBuilder.anUser().userId(15).email("email").firstName("firstName").lastName("lastName").build();
    }
}

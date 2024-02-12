package com.openclassrooms.starterjwt.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import com.openclassrooms.starterjwt.services.SessionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class SessionControllerTest {

    private final String CONTROLLER_PATH = "/api/session";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    SessionService sessionService;

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private SessionController sessionController;

    String getToken() {
        UserDetailsImpl basicUser = new UserDetailsImpl(1L, "yoga@studio.com", "firstname", "lastname", false, "test!1234");
        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(basicUser, null);
        String token = jwtUtils.generateJwtToken(user);
        return (token);
    }

    @Test
    void shouldReturnSession() throws Exception {
        mockMvc
                .perform(
                        get(CONTROLLER_PATH + "/1")
                                .header("Authorization", "Bearer " + this.getToken())
                )
                .andExpect(status().isOk())
                .andExpect(
                        content().json("{ \"id\": 1, \"name\": \"Testsessions1\", \"date\": \"2024-02-07T18:33:10.000+00:00\", \"teacher_id\": 1, \"description\": \"description1\", \"users\": [ 1 ], \"createdAt\": \"2024-02-07T19:33:10\", \"updatedAt\": \"2024-02-07T19:33:10\" }")
                );
    }

    @Test
    void missingjwttoken() throws Exception {
        mockMvc
                .perform(
                        get(CONTROLLER_PATH + "/4")
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldNotReturnSession() throws Exception {
        mockMvc
                .perform(
                        get(CONTROLLER_PATH + "/99999")
                                .header("Authorization", "Bearer " + this.getToken())
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void testFindAll() throws Exception {
        User user1 = new User(1L, "user1@example.com", "User1LastName", "User1FirstName", "password", false, null, null);
        User user2 = new User(2L, "user2@example.com", "User2LastName", "User2FirstName", "password", false, null, null);

        Teacher teacher1 = new Teacher(1L, "Teacher1LastName", "Teacher1FirstName", null, null);
        Teacher teacher2 = new Teacher(2L, "Teacher2LastName", "Teacher2FirstName", null, null);

        List<Session> mockSessions = Arrays.asList(
                new Session(1L, "Testsessions1", new Date(), "Description 1", teacher1, Arrays.asList(user1, user2), null, null),
                new Session(2L, "Testsessions2", new Date(), "Description 2", teacher2, Arrays.asList(user1, user2), null, null)
                // Add more sessions as needed
        );

        MvcResult result = mockMvc.perform(get("/api/session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + this.getToken())
                )
                .andExpect(status().isOk())
                .andReturn();

        List<SessionDto> sessions = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<SessionDto>>() {
        });

        assertNotNull(sessions);

        SessionDto session1 = sessions.get(0);

        assertEquals("Testsessions1", session1.getName());
        assertNotNull(session1.getDate());
        assertEquals("description1", session1.getDescription());
        assertEquals(Arrays.asList(1L), session1.getUsers());

        SessionDto session2 = sessions.get(1);
        assertEquals("Testsessions2", session2.getName());
        assertNotNull(session1.getDate());
        assertEquals("description2", session2.getDescription());
    }

    @Test
    @Transactional
    void testCreateSession() throws Exception {
        SessionDto sessionDto = new SessionDto();
        sessionDto.setName("Test Session");
        sessionDto.setDate(new Date());
        sessionDto.setTeacher_id(1L);
        sessionDto.setDescription("Test Description");

        ObjectMapper objectMapper = new ObjectMapper();
        String sessionDtoJson = objectMapper.writeValueAsString(sessionDto);

        ResultActions resultActions = mockMvc.perform(post("/api/session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sessionDtoJson)
                        .header("Authorization", "Bearer " + this.getToken()))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void testUpdateSession() throws Exception {
        SessionDto sessionDto = new SessionDto();
        sessionDto.setName("Updated Session");
        sessionDto.setDate(new Date());
        sessionDto.setTeacher_id(1L);
        sessionDto.setDescription("Updated Description");

        ObjectMapper objectMapper = new ObjectMapper();
        String sessionDtoJson = objectMapper.writeValueAsString(sessionDto);

        ResultActions resultActions = mockMvc.perform(put("/api/session/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sessionDtoJson)
                        .header("Authorization", "Bearer " + this.getToken()))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void testParticipate() throws Exception {

        Long sessionId = 2L;
        Long userId = 4L;
        String idAsString = sessionId.toString();
        String userIdAsString = userId.toString();

        Session oldsession = sessionRepository.getById(sessionId);
        Integer oldusercount = oldsession.getUsers().size();

        mockMvc.perform(post(CONTROLLER_PATH + "/{id}/participate/{userId}", idAsString, userIdAsString)
                        .header("Authorization", "Bearer " + this.getToken()))
                .andExpect(status().isOk());

        Session newsession = sessionRepository.getById(sessionId);

        assertEquals(oldusercount + 1, newsession.getUsers().size());
    }

    @Test
    public void testParticipateIDnotfound() throws Exception {
        Long invalidId = 9999L;
        Long invalidUserId = 9999L;
        String idAsString = invalidId.toString();
        String userIdAsString = invalidUserId.toString();

        mockMvc.perform(post(CONTROLLER_PATH + "/{id}/participate/{userId}", idAsString, userIdAsString)
                        .header("Authorization", "Bearer " + this.getToken()))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void testNoLongerParticipateerror() throws Exception {
        mockMvc.perform(delete("/api/session/1/participate/3")
                        .header("Authorization", "Bearer " + this.getToken()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void testNoLongerParticipate() throws Exception {
        mockMvc.perform(delete("/api/session/1/participate/1")
                        .header("Authorization", "Bearer " + this.getToken()))
                .andExpect(status().isOk());
    }
}

package com.openclassrooms.starterjwt.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    private final String CONTROLLER_PATH = "/api/user";
    @Autowired
    MockMvc mockMvc;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    EntityManager entityManager;

    String getToken(){
        UserDetailsImpl basicUser = new UserDetailsImpl(1L, "yoga@studio.com", "password", "toto", false, "titi");
        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(basicUser, null);
        String token = jwtUtils.generateJwtToken(user);
        return (token);
    }

    @Test
    void shouldReturnUser() throws Exception {
        mockMvc
                .perform(
                        get(CONTROLLER_PATH + "/1")
                                .header("Authorization", "Bearer " + this.getToken())
                )
                .andExpect(status().isOk())
                .andExpect(
                        content().json("{ \"id\": 1, \"email\": \"yoga@studio.com\", \"lastName\": \"Admin\", \"firstName\": \"Admin\", \"admin\": true, \"createdAt\": \"2024-02-07T19:33:10\", \"updatedAt\": \"2024-02-08T19:35:58\" }")
                );
    }

    @Test
    void shouldNotReturnUser() throws Exception {
        mockMvc
                .perform(
                        get(CONTROLLER_PATH + "/99999")
                                .header("Authorization", "Bearer " + this.getToken())
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldnotfindDeleteUser() throws Exception {
        Long userId = 9999999L;
        User userDetails = mock(User.class);
        when(userDetails.getId()).thenReturn(userId);


        mockMvc.perform(
                        delete(CONTROLLER_PATH + "/999999999")
                                .header("Authorization", "Bearer " + this.getToken())
                )
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void shouldDeleteUser() throws Exception {
        Long userId = 1L;
        User userDetails = mock(User.class);
        when(userDetails.getId()).thenReturn(userId);

        mockMvc.perform(
                    delete(CONTROLLER_PATH + "/1")
                            .header("Authorization", "Bearer " + this.getToken())
                )
                .andExpect(status().isOk());
    }

}

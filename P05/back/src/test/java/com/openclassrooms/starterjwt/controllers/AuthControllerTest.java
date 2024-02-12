package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import com.openclassrooms.starterjwt.models.User;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {
    private final String CONTROLLER_PATH = "/api/auth";
    @Autowired
    MockMvc mockMvc;

    @Autowired
    JwtUtils jwtUtils;

    @Mock
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    EntityManager entityManager;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    @Transactional
    void shouldReturnUser() throws Exception {

        User user = userRepository.getById(1L);
        Authentication authentication = mock(Authentication.class);

        String email = "yoga@studio.com";
        String password = "test!1234";
        UserDetailsImpl userDetails = new UserDetailsImpl(1L, email, "firstname", "lastname", true, password);

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        mockMvc.perform(post(CONTROLLER_PATH + "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"email\": \"yoga@studio.com\", \"password\": \"test!1234\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value(userDetails.getUsername()))
                .andExpect(jsonPath("$.firstName").value(userDetails.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(userDetails.getLastName()));
    }

    @Test
    @Transactional
    void testRegisterUser() throws Exception {
        SignupRequest signupRequest = createSignupRequest("test@example.com", "Doe", "John", "testPassword");

        when(userRepository.existsByEmail(signupRequest.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(signupRequest.getPassword())).thenReturn("hashedPassword");

        mockMvc.perform(post(CONTROLLER_PATH + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"email\": \"test@example.com\", \"lastName\": \"Doe\", \"firstName\": \"John\", \"password\": \"testPassword\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User registered successfully!"));

        reset(userRepository, passwordEncoder);
    }

    private static SignupRequest createSignupRequest(String email, String lastName, String firstName, String password) {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail(email);
        signupRequest.setLastName(lastName);
        signupRequest.setFirstName(firstName);
        signupRequest.setPassword(password);
        return signupRequest;
    }

    @Test
    @Transactional
    void testRegisterUserWithEmailTaken() throws Exception {

        SignupRequest signupRequest = createSignupRequest("test@example.com", "Doe", "John", "test!1234");

        // Mock UserRepository.existsByEmail to return true
        when(userRepository.existsByEmail(signupRequest.getEmail())).thenReturn(true);
        when(passwordEncoder.encode(signupRequest.getPassword())).thenReturn("hashedPassword");

        mockMvc.perform(post(CONTROLLER_PATH + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"lastName\": \"Doe\", \"firstName\": \"John\", \"email\": \"test@example.com\", \"password\": \"test!1234\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User registered successfully!"));

        reset(userRepository, passwordEncoder);

        mockMvc.perform(post(CONTROLLER_PATH + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"lastName\": \"Doe\", \"firstName\": \"John\", \"email\": \"test@example.com\", \"password\": \"test!1234\" }"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Error: Email is already taken!"));

        verify(userRepository, never()).save(any());
        reset(userRepository, passwordEncoder);
    }
}

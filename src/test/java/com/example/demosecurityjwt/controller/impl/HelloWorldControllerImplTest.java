package com.example.demosecurityjwt.controller.impl;

import com.example.demosecurityjwt.model.Role;
import com.example.demosecurityjwt.model.User;
import com.example.demosecurityjwt.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class HelloWorldControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User admin, user;
    private Role adminRole1, adminRole2, userRole;


    @BeforeEach
    void setUp() {
        adminRole1 = new Role("ADMIN");
        adminRole2 = new Role("USER");
        userRole = new Role("USER");
        user = new User("Regular User", "user", passwordEncoder.encode("123456"));
        admin = new User("Admin User", "admin", passwordEncoder.encode("123456"));
        admin.setRoles(List.of(adminRole1, adminRole2));
        user.setRoles(List.of(userRole));

        userRepository.saveAll(List.of(user, admin));


    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void helloAnon_NotLogged_Result() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/hello/anon").param("name", "David"))
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Hello, David"));
    }

    @Test
    void helloUser_NotLogged_Result() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/hello/user"))
                .andExpect(status().isForbidden())
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void helloUser_LoggedUser_Result() throws Exception {
        // Login
        MvcResult mvcResult = mockMvc.perform(
                        get("/login")
                                .param("username", "user")
                                .param("password", "123456")
                )
                .andExpect(status().isOk())
                .andReturn();
        // Parse response to JSON
        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());

        // Get access token
        String token = jsonObject.getString("access_token");

        System.out.println(token);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + token);
        System.out.println(httpHeaders);

        mvcResult = mockMvc.perform(get("/hello/user").headers(httpHeaders))
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Hello User"));
    }

    @Test
    void helloAdmin_LoggedAdmin_Result() throws Exception {
        // Login
        MvcResult mvcResult = mockMvc.perform(
                        get("/login")
                                .param("username", "admin")
                                .param("password", "123456")
                )
                .andExpect(status().isOk())
                .andReturn();
        // Parse response to JSON
        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());

        // Get access token
        String token = jsonObject.getString("access_token");

        System.out.println(token);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + token);
        System.out.println(httpHeaders);

        mvcResult = mockMvc.perform(get("/hello/admin").headers(httpHeaders))
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Hello Admin " + admin.getUsername()));
    }
}
package com.justinmtech.webank.controller;

import com.justinmtech.webank.model.User;
import com.justinmtech.webank.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RegistrationController controller;

    @Autowired
    UserRepository userRepository;

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void registerUser() throws Exception {
        String username = "justin.mitchell09@gmail.com";
        String password = "password";
        String firstName = "justin";
        String lastName = "mitchell";
        String phoneNumber = "6013334444";
        mockMvc.perform(post("/register")
                        .contentType(APPLICATION_FORM_URLENCODED) //from MediaType
                        .param("username", username)
                        .param("password", password)
                        .param("firstName", firstName)
                        .param("lastName", lastName)
                        .param("phoneNumber", phoneNumber))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString("A verification has been sent to your email.")));
        Optional<User> user = userRepository.findByUsername(username);
        assertTrue(user.isPresent());
        assertFalse(user.get().isEnabled());
        assertEquals(username, user.get().getUsername());
        //Password should be encoded, therefore not equal
        assertNotEquals(password, user.get().getPassword());
        assertEquals(firstName, user.get().getFirstName());
        assertEquals(lastName, user.get().getLastName());
        assertEquals(phoneNumber, user.get().getPhoneNumber());
    }
}
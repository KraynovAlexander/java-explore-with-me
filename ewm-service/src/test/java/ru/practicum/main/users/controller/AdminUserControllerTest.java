package ru.practicum.main.users.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.main.users.dto.UserDto;
import ru.practicum.main.users.service.AdminUserService;

import java.nio.charset.StandardCharsets;


import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AdminUserController.class)
class AdminUserControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    AdminUserService adminUserService;

    @Autowired
    private MockMvc mockMvc;

    private static UserDto userDto;

    @BeforeAll
    static void beforeAll() {

        userDto = new UserDto();
        userDto.setId(1L);
        userDto.setName("Герда");
        userDto.setEmail("герда@email.ru");

    }

    @Test
    void addNewUser() throws Exception {

        when(adminUserService.addUser(any()))
                .thenReturn(userDto);

        mockMvc.perform(post("/admin/users/")
                    .content(objectMapper.writeValueAsString(userDto))
                    .characterEncoding(StandardCharsets.UTF_8)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(userDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(userDto.getName())));

    }

}
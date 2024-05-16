package mock_testing_l8.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mock_testing_l8.dto.AppErrorDto;
import mock_testing_l8.dto.AuthUserCreateDto;
import mock_testing_l8.dto.AuthUserGetDto;
import mock_testing_l8.dto.AuthUserUpdateDto;
import mock_testing_l8.service.AuthUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest
class AuthUserControllerTestWithWebMvcTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    AuthUserService authUserService;

    @Autowired
    ObjectMapper mapper;

    @Test
    void create() throws Exception {
        AuthUserCreateDto createDto = AuthUserCreateDto.builder()
                .username("Panda")
                .email("panda@gmail.com")
                .password("9779")
                .build();
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/authUser")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(createDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        verify(authUserService, times(1)).create(createDto);
    }

    @Test
    void createBadRequest() throws Exception {
        AuthUserCreateDto createDto = AuthUserCreateDto.builder()
                .username("")
                .email("panda@gmail.com")
                .password("9779")
                .build();
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/authUser")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(createDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(400))
                .andExpect(jsonPath("$.errorPath").value("/api/authUser"))
                .andExpect(jsonPath("$.friendlyMessage").value("Invalid Input"))
                .andExpect(jsonPath("$.developerMessage.username").value("username can not be blank"));
    }

    @Test
    void update() throws Exception {
        AuthUserUpdateDto updateDto = AuthUserUpdateDto.builder()
                .id(1)
                .username("Simple")
                .email("simple@gmail.com")
                .build();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/authUser")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(updateDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String contentAsString = response.getContentAsString();
        assertEquals("Successfully Updated - User", contentAsString);
        verify(authUserService, times(1)).update(updateDto);
    }

    @Test
    void updateBadRequest() throws Exception {
        AuthUserUpdateDto updateDto = AuthUserUpdateDto.builder()
                .id(1)
                .username("Simple")
                .email("")
                .build();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/authUser")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(updateDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        AppErrorDto errorDto = mapper.readValue(contentAsString, AppErrorDto.class);
        assertEquals(400, errorDto.getErrorCode());
        assertEquals("Invalid Input", errorDto.getFriendlyMessage());
        assertEquals("/api/authUser", errorDto.getErrorPath());
    }

    @Test
    void delete() throws Exception {
        int deleteId = 1;
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/authUser/1")
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        assertEquals("Successfully Deleted - User", contentAsString);
        verify(authUserService, times(1)).delete(deleteId);
    }

    @Test
    void get() throws Exception {
        when(authUserService.get(1)).thenReturn(new AuthUserGetDto("Panda", "panda@gmail.com"));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/authUser/1")
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.username").value("Panda"));
        verify(authUserService, times(1)).get(1);
    }

    @Test
    void list() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/authUser")
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(authUserService, times(1)).getAll();
    }
}
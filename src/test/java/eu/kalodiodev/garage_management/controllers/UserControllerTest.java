package eu.kalodiodev.garage_management.controllers;

import eu.kalodiodev.garage_management.command.UserCommand;
import eu.kalodiodev.garage_management.domains.User;
import eu.kalodiodev.garage_management.exceptions.NotFoundException;
import eu.kalodiodev.garage_management.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    void index_users() throws Exception {
        User user1 = new User();
        user1.setId(1L);

        User user2 = new User();
        user2.setId(2L);

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);


        when(userService.all()).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/index"))
                .andExpect(model().attribute("users", hasSize(2)));
    }

    @Test
    void displayUser() throws Exception {
        User user = new User();
        user.setId(1L);

        when(userService.findById(1L)).thenReturn(user);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/show"))
                .andExpect(model().attribute("user", user));
    }

    @Test
    void displayUserNotFound() throws Exception {
        when(userService.findById(1L)).thenThrow(new NotFoundException("User not found"));

        mockMvc.perform(get("/users/1"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void initCreateUser() throws Exception {
        mockMvc.perform(get("/users/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/create"))
                .andExpect(model().attributeExists("userCommand"));
    }

    @Test
    void storeUser() throws Exception {
        User user = new User();
        user.setId(1L);

        when(userService.register(any(UserCommand.class))).thenReturn(user);

        mockMvc.perform(post("/users/")
                .param("firstName", "John")
                .param("lastName", "Doe")
                .param("email", "john@example.com")
                .param("password", "password")
                .param("passwordConfirm", "password")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users/1"))
                .andExpect(flash().attributeExists("message"));
    }

    @Test
    void storeUserValidateName() throws Exception {
        mockMvc.perform(post("/users")
                .param("email", "test@example.com")
                .param("firstName", "")
                .param("lastName", "")
        )
                .andExpect(model().attributeHasFieldErrors("userCommand", "firstName"))
                .andExpect(model().attributeHasFieldErrors("userCommand", "lastName"));
    }

    @Test
    void storeUserValidateEmail() throws Exception {
        mockMvc.perform(post("/users").param("email", ""))
                .andExpect(model().attributeHasFieldErrors("userCommand", "email"));

        when(userService.isEmailAlreadyInUse(any())).thenReturn(true);

        mockMvc.perform(post("/users").param("email", "test@example.com"))
                .andExpect(model().attributeHasFieldErrors("userCommand", "email"));
    }

    @Test
    void storeUserValidatePassword() throws Exception {
        User user = new User();
        user.setId(1L);

        when(userService.register(any())).thenReturn(user);

        // No errors
        mockMvc.perform(post("/users")
                .param("firstName", "John")
                .param("lastName", "Doe")
                .param("email", "john@example.com")
                .param("password", "12345678")
                .param("passwordConfirm", "12345678")
        )
                .andExpect(model().hasNoErrors());


        // Password confirm required
        mockMvc.perform(post("/users")
                .param("password", "12345678")
                .param("passwordConfirm", "")
        )
                .andExpect(model().attributeHasFieldErrors("userCommand", "passwordConfirm"));

        // Password required
        mockMvc.perform(post("/users").param("password", ""))
                .andExpect(model().attributeHasFieldErrors("userCommand", "password"));

        // Password must be at least 8 chars long
        mockMvc.perform(post("/users").param("password", "1234567"))
                .andExpect(model().attributeHasFieldErrors("userCommand", "password"));

        // Password Confirm must be at least 8 chars long
        mockMvc.perform(post("/users").param("passwordConfirm", "1234567"))
                .andExpect(model().attributeHasFieldErrors("userCommand", "passwordConfirm"));

        // Password and Password confirm should match
        mockMvc.perform(post("/users")
                .param("password", "12345678")
                .param("passwordConfirm", "13244343")
        )
                .andExpect(model().attributeHasErrors("userCommand"));

    }
}

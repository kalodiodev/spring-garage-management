package eu.kalodiodev.garage_management.controllers;

import eu.kalodiodev.garage_management.command.UserInfoCommand;
import eu.kalodiodev.garage_management.converter.UserToUserInfoCommand;
import eu.kalodiodev.garage_management.domains.User;
import eu.kalodiodev.garage_management.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ProfileControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserToUserInfoCommand userToUserInfoCommand;

    @InjectMocks
    private ProfileController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    void edit_profile() throws Exception {
        when(userToUserInfoCommand.convert(any(User.class))).thenReturn(new UserInfoCommand());

        mockMvc.perform(get("/profile"))
                .andExpect(status().isOk())
                .andExpect(view().name("profile/edit"))
                .andExpect(model().attributeExists("userInfoCommand"))
                .andExpect(model().attributeExists("passwordCommand"));
    }

    @Test
    void update_profile() throws Exception {
        mockMvc.perform(patch("/profile")
                .param("email", "test@example.com")
                .param("firstName", "John")
                .param("lastName", "Doe")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/profile"))
                .andExpect(flash().attributeExists("message"));

        verify(userService, times(1)).updateUserInfo(any(User.class), any(UserInfoCommand.class));
    }

    @Test
    void update_profile_validate_name() throws Exception {
        mockMvc.perform(patch("/profile")
                .param("email", "test@example.com")
                .param("firstName", "")
                .param("lastName", "")
        )
                .andExpect(model().attributeHasFieldErrors("userInfoCommand", "firstName"))
                .andExpect(model().attributeHasFieldErrors("userInfoCommand", "lastName"));
    }

    @Test
    void update_profile_validate_email() throws Exception {
        // Empty Email
        mockMvc.perform(patch("/profile").param("email", ""))
                .andExpect(model().attributeHasFieldErrors("userInfoCommand", "email"));

        // Not valid email format
        mockMvc.perform(patch("/profile").param("email", "notemail"))
                .andExpect(model().attributeHasFieldErrors("userInfoCommand", "email"));

        // Email already in use by other user
        when(userService.isEmailAlreadyInUseExceptUser(anyString(), any(User.class))).thenReturn(true);
        mockMvc.perform(patch("/profile").param("email", "test2@example.com"))
                .andExpect(model().attributeHasFieldErrors("userInfoCommand", "email"));
    }

    @Test
    void update_user_password() throws Exception {
        mockMvc.perform(patch("/profile/password")
                .param("password", "my_new_password")
                .param("passwordConfirm", "my_new_password")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/profile"))
                .andExpect(flash().attributeExists("message"));
    }

    @Test
    void update_user_validate_password() throws Exception {

        // No errors
        mockMvc.perform(patch("/profile/password")
                .param("password", "12345678")
                .param("passwordConfirm", "12345678")
        )
                .andExpect(model().hasNoErrors());


        // Password confirm required
        mockMvc.perform(patch("/profile/password")
                .param("password", "12345678")
                .param("passwordConfirm", "")
        )
                .andExpect(model().attributeHasFieldErrors("passwordCommand", "passwordConfirm"));

        // Password required
        mockMvc.perform(patch("/profile/password").param("password", ""))
                .andExpect(model().attributeHasFieldErrors("passwordCommand", "password"));

        // Password must be at least 8 chars long
        mockMvc.perform(patch("/profile/password").param("password", "1234567"))
                .andExpect(model().attributeHasFieldErrors("passwordCommand", "password"));

        // Password Confirm must be at least 8 chars long
        mockMvc.perform(patch("/profile/password").param("passwordConfirm", "1234567"))
                .andExpect(model().attributeHasFieldErrors("passwordCommand", "passwordConfirm"));

        // Password and Password confirm should match
        mockMvc.perform(patch("/profile/password")
                .param("password", "12345678")
                .param("passwordConfirm", "13244343")
        )
                .andExpect(model().attributeHasErrors("passwordCommand"));

    }
}

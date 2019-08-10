package eu.kalodiodev.garage_management.services.jpa;

import eu.kalodiodev.garage_management.command.UserCommand;
import eu.kalodiodev.garage_management.command.UserInfoCommand;
import eu.kalodiodev.garage_management.converter.UserCommandToUser;
import eu.kalodiodev.garage_management.domains.Role;
import eu.kalodiodev.garage_management.domains.User;
import eu.kalodiodev.garage_management.exceptions.NotFoundException;
import eu.kalodiodev.garage_management.repositories.UserRepository;
import eu.kalodiodev.garage_management.services.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JpaUserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    UserCommandToUser userCommandToUser;

    @Mock
    RoleService roleService;

    @InjectMocks
    JpaUserServiceImpl userService;

    private static final Long USER_1_ID = 1L;
    private static final Long USER_2_ID = 2L;

    private User user1;
    private User user2;


    @BeforeEach
    void setUp() {
        user1 = new User();
        user1.setId(1L);
        user1.setEmail("test1@example.com");

        user2 = new User();
        user2.setId(2L);
        user2.setEmail("test2@example.com");
    }

    @Test
    void find_all_users() {
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        when(userRepository.findAll()).thenReturn(users);

        assertEquals(2, userService.all().size());
    }

    @Test
    void save_user() {
        when(userRepository.save(any(User.class))).thenReturn(user1);

        assertEquals(user1, userService.save(user1));
    }

    @Test
    void find_user_by_id() {
        when(userRepository.findById(USER_1_ID)).thenReturn(Optional.of(user1));

        assertEquals(user1, userService.findById(USER_1_ID));
    }

    @Test
    void user_not_found() {
        when(userRepository.findById(USER_1_ID)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.findById(USER_1_ID));
    }

    @Test
    void register_user_command() {
        user1.setPassword("password");

        when(userRepository.save(any(User.class))).thenReturn(user1);
        when(userCommandToUser.convert(any(UserCommand.class))).thenReturn(user1);
        when(passwordEncoder.encode(anyString())).thenReturn("asdffdsadf");
        when(roleService.findByName(anyString())).thenReturn(new Role());

        assertEquals(user1, userService.register(new UserCommand()));
    }

    @Test
    void is_email_already_in_use() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(new User()));

        assertTrue(userService.isEmailAlreadyInUse("test@example.com"));

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        assertFalse(userService.isEmailAlreadyInUse("test@example.com"));
    }

    @Test
    void is_email_already_in_use_except_given_user() {
        String email = "other@example.com";

        assertFalse(userService.isEmailAlreadyInUseExceptUser(user1.getEmail(), user1));

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(new User()));

        assertTrue(userService.isEmailAlreadyInUseExceptUser(email, user1));

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertFalse(userService.isEmailAlreadyInUseExceptUser(email, user1));
    }


    @Test
    void delete_user() {
        when(userRepository.findById(USER_1_ID)).thenReturn(Optional.of(user1));

        userService.delete(USER_1_ID);

        verify(userRepository, times(1)).delete(user1);
    }

    @Test
    void delete_user_not_found() {
        when(userRepository.findById(USER_1_ID)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.delete(USER_1_ID));
    }

    @Test
    void update_given_user_info() {
        UserInfoCommand userInfoCommand = new UserInfoCommand();
        userInfoCommand.setId(1L);
        userInfoCommand.setEmail("test2@example");
        userInfoCommand.setFirstName("John");
        userInfoCommand.setLastName("Doe");

        when(userRepository.save(any(User.class))).thenReturn(user1);

        assertEquals(user1, userService.updateUserInfo(user1, userInfoCommand));
    }
}

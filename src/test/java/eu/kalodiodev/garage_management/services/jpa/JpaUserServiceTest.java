package eu.kalodiodev.garage_management.services.jpa;

import eu.kalodiodev.garage_management.domains.User;
import eu.kalodiodev.garage_management.exceptions.NotFoundException;
import eu.kalodiodev.garage_management.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JpaUserServiceTest {

    @Mock
    UserRepository userRepository;

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

        user2 = new User();
        user2.setId(2L);
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
}

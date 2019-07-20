package eu.kalodiodev.garage_management.services.jpa;

import eu.kalodiodev.garage_management.domains.User;
import eu.kalodiodev.garage_management.repositories.UserRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JpaUserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    JpaUserServiceImpl userService;

    @Before
    void setUp() {

    }

    @Test
    void save_user() {
        User user = new User();
        user.setId(1L);

        when(userRepository.save(any(User.class))).thenReturn(user);

        assertEquals(user, userService.save(user));
    }
}

package eu.kalodiodev.garage_management.converter;

import eu.kalodiodev.garage_management.command.UserInfoCommand;
import eu.kalodiodev.garage_management.converter.values.UserValues;
import eu.kalodiodev.garage_management.domains.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserToUserInfoCommandTest {

    private UserToUserInfoCommand converter;

    @BeforeEach
    public void setUp() throws Exception {
        converter = new UserToUserInfoCommand();
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new User()));
    }

    @Test
    public void convert() throws Exception {
        // given
        User user = new User();
        user.setId(UserValues.ID_VALUE);
        user.setEmail(UserValues.EMAIL_VALUE);
        user.setFirstName(UserValues.FIRST_NAME_VALUE);
        user.setLastName(UserValues.LAST_NAME_VALUE);

        // when
        UserInfoCommand userInfoCommand = converter.convert(user);

        // then
        assertNotNull(user);
        assertEquals(UserValues.ID_VALUE, userInfoCommand.getId());
        assertEquals(UserValues.EMAIL_VALUE, userInfoCommand.getEmail());
        assertEquals(UserValues.FIRST_NAME_VALUE, userInfoCommand.getFirstName());
        assertEquals(UserValues.LAST_NAME_VALUE, userInfoCommand.getLastName());
    }
}

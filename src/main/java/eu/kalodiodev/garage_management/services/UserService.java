package eu.kalodiodev.garage_management.services;

import eu.kalodiodev.garage_management.command.UserCommand;
import eu.kalodiodev.garage_management.command.UserInfoCommand;
import eu.kalodiodev.garage_management.domains.User;

import java.util.List;

public interface UserService {

    User findById(Long id);

    List<User> all();

    User save(User user);

    User register(UserCommand command);

    boolean isEmailAlreadyInUse(String email);

    void delete(Long id);

    User updateUserInfo(User user, UserInfoCommand userInfoCommand);
}

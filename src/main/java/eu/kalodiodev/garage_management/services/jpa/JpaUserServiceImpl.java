package eu.kalodiodev.garage_management.services.jpa;

import eu.kalodiodev.garage_management.command.PasswordCommand;
import eu.kalodiodev.garage_management.command.UserCommand;
import eu.kalodiodev.garage_management.command.UserInfoCommand;
import eu.kalodiodev.garage_management.converter.UserCommandToUser;
import eu.kalodiodev.garage_management.converter.UserToUserInfoCommand;
import eu.kalodiodev.garage_management.domains.User;
import eu.kalodiodev.garage_management.exceptions.NotFoundException;
import eu.kalodiodev.garage_management.repositories.UserRepository;
import eu.kalodiodev.garage_management.services.RoleService;
import eu.kalodiodev.garage_management.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JpaUserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserCommandToUser userCommandToUser;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final UserToUserInfoCommand userToUserInfoCommand;

    public JpaUserServiceImpl(UserRepository userRepository,
                              UserCommandToUser userCommandToUser,
                              PasswordEncoder passwordEncoder,
                              RoleService roleService,
                              UserToUserInfoCommand userToUserInfoCommand) {

        this.userRepository = userRepository;
        this.userCommandToUser = userCommandToUser;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.userToUserInfoCommand = userToUserInfoCommand;
    }

    @Override
    public User findById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            throw new NotFoundException("User not found");
        }

        return userOptional.get();
    }

    @Override
    public UserInfoCommand findInfoCommandById(Long id) {
        return userToUserInfoCommand.convert(findById(id));
    }

    @Override
    public List<User> all() {
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User register(UserCommand userCommand) {
        User user = userCommandToUser.convert(userCommand);

        if (user == null) {
            return null;
        }

        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);

        user.addRole(roleService.findByName("ADMIN"));

        return save(user);
    }

    @Override
    public boolean isEmailAlreadyInUse(String email) {

        Optional<User> userOptional = userRepository.findByEmail(email);

        return userOptional.isPresent();
    }

    @Override
    public boolean isEmailAlreadyInUseExceptUser(String email, User user) {
        if (email.equals(user.getEmail())) {
            return false;
        }

        return isEmailAlreadyInUse(email);
    }

    @Override
    public void delete(Long id) {
        User user = findById(id);

        userRepository.delete(user);
    }

    @Override
    public User updateUserInfo(User user, UserInfoCommand userInfoCommand) {

        user.setEmail(userInfoCommand.getEmail());
        user.setFirstName(userInfoCommand.getFirstName());
        user.setLastName(userInfoCommand.getLastName());

        return save(user);
    }

    @Override
    public User updatePassword(User user, PasswordCommand passwordCommand) {
        String password = passwordEncoder.encode(passwordCommand.getPassword());
        user.setPassword(password);

        return save(user);
    }
}

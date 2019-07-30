package eu.kalodiodev.garage_management.services.jpa;

import eu.kalodiodev.garage_management.command.UserCommand;
import eu.kalodiodev.garage_management.converter.UserCommandToUser;
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

    public JpaUserServiceImpl(UserRepository userRepository, UserCommandToUser userCommandToUser, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.userCommandToUser = userCommandToUser;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
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
    public void delete(Long id) {
        User user = findById(id);

        userRepository.delete(user);
    }
}

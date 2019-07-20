package eu.kalodiodev.garage_management.services.jpa;

import eu.kalodiodev.garage_management.domains.User;
import eu.kalodiodev.garage_management.repositories.UserRepository;
import eu.kalodiodev.garage_management.services.UserService;
import org.springframework.stereotype.Service;

@Service
public class JpaUserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public JpaUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}

package eu.kalodiodev.garage_management.services;

import eu.kalodiodev.garage_management.domains.User;

import java.util.List;

public interface UserService {

    List<User> all();

    User save(User user);
}

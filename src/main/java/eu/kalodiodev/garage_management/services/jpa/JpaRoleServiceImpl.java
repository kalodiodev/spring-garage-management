package eu.kalodiodev.garage_management.services.jpa;

import eu.kalodiodev.garage_management.domains.Role;
import eu.kalodiodev.garage_management.exceptions.NotFoundException;
import eu.kalodiodev.garage_management.repositories.RoleRepository;
import eu.kalodiodev.garage_management.services.RoleService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JpaRoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public JpaRoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role findByName(String name) {
        Optional<Role> roleOptional = roleRepository.findByName(name);

        if(roleOptional.isEmpty()) {
            throw new NotFoundException("Role not found");
        }

        return roleOptional.get();
    }
}

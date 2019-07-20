package eu.kalodiodev.garage_management.services.jpa;

import eu.kalodiodev.garage_management.domains.Role;
import eu.kalodiodev.garage_management.repositories.RoleRepository;
import eu.kalodiodev.garage_management.services.RoleService;
import org.springframework.stereotype.Service;

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
}

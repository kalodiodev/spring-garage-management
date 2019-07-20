package eu.kalodiodev.garage_management.services.jpa;

import eu.kalodiodev.garage_management.domains.Role;
import eu.kalodiodev.garage_management.repositories.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JpaRoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private JpaRoleServiceImpl roleService;

    @Test
    void save_role() {
        Role role = new Role();
        role.setId(1L);

        when(roleRepository.save(role)).thenReturn(role);

        assertEquals(role, roleService.save(role));
    }
}

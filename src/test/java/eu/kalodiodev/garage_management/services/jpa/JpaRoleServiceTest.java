package eu.kalodiodev.garage_management.services.jpa;

import eu.kalodiodev.garage_management.domains.Role;
import eu.kalodiodev.garage_management.exceptions.NotFoundException;
import eu.kalodiodev.garage_management.repositories.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Test
    void find_role_by_name() {
        Role role = new Role();
        role.setId(1L);
        role.setName("ROLE_USER");

        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(role));

        assertEquals(role, roleService.findByName("ROLE_USER"));
    }

    @Test
    void find_role_by_name_not_found() {
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> roleService.findByName("ROLE_USER"));
    }
}

package com.can.zupuserservice.unit.service;

import com.can.zupuserservice.core.utilities.result.abstracts.DataResult;
import com.can.zupuserservice.core.utilities.result.abstracts.Result;
import com.can.zupuserservice.data.entity.Role;
import com.can.zupuserservice.repository.RoleRepository;
import com.can.zupuserservice.service.concretes.RoleService;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

public class RoleServiceTest {

    private RoleRepository roleRepository;
    private RoleService roleService;

    @BeforeEach
    void setUp() {
        roleRepository = Mockito.mock(RoleRepository.class);
        roleService = new RoleService(roleRepository);
    }

    @Test
    @DisplayName("Should get role by name")
    void shouldGetRoleByName() {
        String roleName = "test1";

        Role role = new Role(1L, roleName, "test1", null);
        Optional<Role> existingRole = Optional.of(role);
        Mockito.when(roleRepository.findByName(roleName)).thenReturn(existingRole);

        DataResult<Role> result = roleService.getByName(roleName);

        Mockito.verify(roleRepository).findByName(roleName);

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.getStatus());
        Assertions.assertEquals(result.getData(), role);
    }

    @Test
    @DisplayName("Should get all roles")
    void shouldGetAllRoles() {
        List<Role> roles = List.of(new Role(1L, "test1", "test1", null), new Role(1L, "test2", "test1", null));
        Mockito.when(roleRepository.findAll()).thenReturn(roles);

        DataResult<List<Role>> result = roleService.getAll();

        Mockito.verify(roleRepository).findAll();

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.getStatus());
        Assertions.assertEquals(result.getData(), roles);
    }

    @Test
    @DisplayName("Should not add role when role name is used before")
    void shouldNotAddRoleWhenNameIsUsedBefore() {
        Optional<Role> existingRole = Optional.of(new Role(1L, "test1", "test1", null));
        Mockito.when(roleRepository.findByName("test1")).thenReturn(existingRole);

        Role role = new Role(1L, "test1", "test1", null);

        Result result = roleService.add(role);

        Mockito.verify(roleRepository).findByName("test1");

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.getStatus());
    }

    @Test
    @DisplayName("Should add role")
    void shouldAddRole() {
        Optional<Role> empty = Optional.empty();
        Mockito.when(roleRepository.findByName("test1")).thenReturn(empty);

        Role role = new Role(1L, "test1", "test1", null);

        Mockito.when(roleRepository.save(role)).thenReturn(role);

        Result result = roleService.add(role);

        Mockito.verify(roleRepository).findByName("test1");
        Mockito.verify(roleRepository).save(role);

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.getStatus());
    }

}

package edu.tinkoff.tinkoffbackendacademypetproject.service;

import edu.tinkoff.tinkoffbackendacademypetproject.CommonAbstractTest;
import edu.tinkoff.tinkoffbackendacademypetproject.exceptions.RoleNotFoundException;
import edu.tinkoff.tinkoffbackendacademypetproject.model.Role;
import edu.tinkoff.tinkoffbackendacademypetproject.model.RoleEntity;
import edu.tinkoff.tinkoffbackendacademypetproject.repositories.RoleRepository;
import edu.tinkoff.tinkoffbackendacademypetproject.services.RoleService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoleServiceTest extends CommonAbstractTest {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    @AfterEach
    void clear() {
        roleRepository.deleteAll();
        jdbcTemplate.execute("ALTER SEQUENCE role_pk_seq RESTART");
    }

    @DisplayName("Test saving all types of roles")
    @ParameterizedTest
    @EnumSource(Role.class)
    void saveRole(Role role) {
        // given

        // when
        RoleEntity entity = new RoleEntity(null, role, null);
        RoleEntity savedRole = roleService.save(entity);

        // then
        assertEquals(savedRole.getName(), role);
    }

    @DisplayName("Getting role by name")
    @ParameterizedTest
    @EnumSource(Role.class)
    void getRoleByName(Role role) {
        // given
        RoleEntity roleEntity = new RoleEntity(null, role, null);
        RoleEntity savedRole = roleService.save(roleEntity);

        // when
        RoleEntity roleByName = roleService.getRoleByName(role);

        // then
        assertEquals(roleByName.getName(), savedRole.getName());
    }

    @DisplayName("Getting role by id")
    @ParameterizedTest
    @EnumSource(Role.class)
    void getRoleById(Role role) {
        // given
        RoleEntity roleEntity = new RoleEntity(null, role, null);
        RoleEntity savedRole = roleService.save(roleEntity);

        // when
        RoleEntity roleByName = roleService.getRoleById(savedRole.getId());

        // then
        assertEquals(roleByName.getName(), savedRole.getName());
    }

    @DisplayName("Getting role by not existing id")
    @ParameterizedTest
    @EnumSource(Role.class)
    void getRoleByIdThrowRoleNotFound(Role role) {
        // given
        RoleEntity roleEntity = new RoleEntity(null, role, null);
        RoleEntity savedRole = roleService.save(roleEntity);

        // when
        var exception = assertThrows(
                RoleNotFoundException.class,
                () -> roleService.getRoleById(savedRole.getId() + 1)
        );

        // then
        assertEquals("Роль с id=" + (int) (savedRole.getId() + 1) + " не была найдена", exception.getMessage());
    }

    @DisplayName("Check if role exists")
    @Test
    void checkIfRoleExists() {
        // given
        RoleEntity roleEntity = new RoleEntity(null, Role.ROLE_ADMIN, null);

        // when
        RoleEntity savedRole = roleService.save(roleEntity);

        // then
        assertTrue(roleService.roleExists(Role.ROLE_ADMIN));
        assertFalse(roleService.roleExists(Role.ROLE_USER));
        assertFalse(roleService.roleExists(Role.ROLE_MODERATOR));
    }

    @DisplayName("Get all roles to assign")
    @Test
    void getAllRolesInsteadOfAdmin() {
        // given
        Role[] roles = Role.values();
        for (Role role : roles) {
            RoleEntity roleEntity = new RoleEntity(null, role, null);
            roleService.save(roleEntity);
        }

        // when
        List<RoleEntity> roleEntityList = roleService.getAllRolesInsteadOfAdmin();

        // then
        assertEquals(roles.length - 1, roleEntityList.size());
        assertEquals(Role.ROLE_MODERATOR, roleEntityList.get(0).getName());
        assertEquals(Role.ROLE_USER, roleEntityList.get(1).getName());
    }
}

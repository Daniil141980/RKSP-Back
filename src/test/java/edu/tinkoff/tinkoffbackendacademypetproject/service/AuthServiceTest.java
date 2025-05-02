package edu.tinkoff.tinkoffbackendacademypetproject.service;

import edu.tinkoff.tinkoffbackendacademypetproject.CommonAbstractTest;
import edu.tinkoff.tinkoffbackendacademypetproject.exceptions.EntityModelNotFoundException;
import edu.tinkoff.tinkoffbackendacademypetproject.exceptions.RoleNotFoundException;
import edu.tinkoff.tinkoffbackendacademypetproject.model.Account;
import edu.tinkoff.tinkoffbackendacademypetproject.model.Role;
import edu.tinkoff.tinkoffbackendacademypetproject.model.RoleEntity;
import edu.tinkoff.tinkoffbackendacademypetproject.repositories.AccountRepository;
import edu.tinkoff.tinkoffbackendacademypetproject.repositories.RoleRepository;
import edu.tinkoff.tinkoffbackendacademypetproject.services.AuthService;
import edu.tinkoff.tinkoffbackendacademypetproject.services.RoleService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AuthServiceTest extends CommonAbstractTest {
    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    @AfterEach
    void clear() {
        accountRepository.deleteAll();
        jdbcTemplate.execute("ALTER SEQUENCE account_pk_seq RESTART");
        roleRepository.deleteAll();
        jdbcTemplate.execute("ALTER SEQUENCE role_pk_seq RESTART");
    }

    @DisplayName("Login account")
    @ParameterizedTest(name = "{index} - account is login")
    @CsvSource(value = {
            "user@user.ru, 12345"
    })
    void saveInformationAboutAccountTest(String email, String password) throws EntityModelNotFoundException {
        // given
        createBeforeStart();

        // when
        var accountWhen = authService.login(email, password).getFirst();

        // then
        var account = accountRepository.findById(2L);
        assertTrue(account.isPresent());
        var accountGet = account.get();
        assertEquals(accountWhen.getEmail(), accountGet.getEmail());
        assertEquals(accountWhen.getNickname(), accountGet.getNickname());
        assertEquals(accountWhen.getId(), accountGet.getId());
    }

    @DisplayName("Register account")
    @ParameterizedTest(name = "{index} - account is register")
    @CsvSource(value = {
            "user@user.ru, user, 12345"
    })
    void saveInformationAboutAccountTest(String email, String nickname, String password) throws EntityModelNotFoundException {
        // given
        insertRoles();
        // when
        var accountWhen = authService.register(email, nickname, password).getFirst();

        // then
        var accounts = accountRepository.findAll();
        assertEquals(1, accounts.size());
        var accountGet = accounts.get(0);
        assertEquals(accountWhen.getEmail(), accountGet.getEmail());
        assertEquals(accountWhen.getNickname(), accountGet.getNickname());
        assertEquals(accountWhen.getId(), accountGet.getId());
    }

    private void createBeforeStart() throws RoleNotFoundException {
        insertRoles();
        accountRepository.save(new Account(null, "dan@dam.ru", passwordEncoder.encode("1234"), "Daniil K", null, null, null, null, null, false, roleService.getRoleByName(Role.ROLE_USER), null, null, null));
        accountRepository.save(new Account(null, "user@user.ru", passwordEncoder.encode("12345"), "User", null, null, null, null, null, false, roleService.getRoleByName(Role.ROLE_USER), null, null, null));
    }

    private void insertRoles() {
        Role[] roles = Role.values();
        for (Role role : roles) {
            if (!roleService.roleExists(role)) {
                RoleEntity roleEntity = new RoleEntity(null, role, null);
                roleService.save(roleEntity);
            }
        }
    }
}

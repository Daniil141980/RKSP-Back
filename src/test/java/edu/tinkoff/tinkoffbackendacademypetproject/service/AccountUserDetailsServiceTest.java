package edu.tinkoff.tinkoffbackendacademypetproject.service;

import edu.tinkoff.tinkoffbackendacademypetproject.CommonAbstractTest;
import edu.tinkoff.tinkoffbackendacademypetproject.exceptions.AccountAlreadyExistException;
import edu.tinkoff.tinkoffbackendacademypetproject.exceptions.RoleNotFoundException;
import edu.tinkoff.tinkoffbackendacademypetproject.model.Account;
import edu.tinkoff.tinkoffbackendacademypetproject.model.Role;
import edu.tinkoff.tinkoffbackendacademypetproject.model.RoleEntity;
import edu.tinkoff.tinkoffbackendacademypetproject.repositories.AccountRepository;
import edu.tinkoff.tinkoffbackendacademypetproject.repositories.RoleRepository;
import edu.tinkoff.tinkoffbackendacademypetproject.services.AccountUserDetailsService;
import edu.tinkoff.tinkoffbackendacademypetproject.services.AuthService;
import edu.tinkoff.tinkoffbackendacademypetproject.services.RoleService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.*;


class AccountUserDetailsServiceTest extends CommonAbstractTest {
    @Autowired
    private AuthService authService;

    @Autowired
    private AccountUserDetailsService accountUserDetailsService;

    @Autowired
    private AccountRepository accountRepository;

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
        Role[] roles = Role.values();
        for (Role role : roles) {
            RoleEntity roleEntity = new RoleEntity(null, role, null);
            roleService.save(roleEntity);
        }
        accountRepository.deleteAll();
        jdbcTemplate.execute("ALTER SEQUENCE account_pk_seq RESTART");
    }

    @DisplayName("Register account")
    @ParameterizedTest(name = "{index} - account register {2}")
    @CsvSource(value = {
            "dan@dan.ru, 100248, Daniil Korshunov",
            "abc1@tink.com, 123, Oleg Tink"
    })
    void registerTest(String email, String password, String nickname) throws RoleNotFoundException {
        // given
        // when
        authService.register(email, nickname, password);

        // then
        var accounts = accountRepository.findAll();
        assertEquals(1, accounts.size());
        var account = accounts.get(0);
        assertEquals(1, account.getId());
        assertEquals(email, account.getEmail());
        assertEquals(nickname, account.getNickname());
    }

    @DisplayName("Register account with email throw")
    @ParameterizedTest(name = "{index} - account not register {2}")
    @CsvSource(value = {
            "dan@dan.ru, 100248, Daniil Korshunov",
            "abc1@tink.com, 123, Oleg Tink"
    })
    void registerWithEmailThrowTest(String email, String password, String nickname) throws RoleNotFoundException {
        // given
        var accountGiven = new Account(null, email, password, nickname, null, null, null, null, null, false, roleService.getRoleByName(Role.ROLE_USER), null, null, null);
        accountRepository.save(accountGiven);
        accountGiven.setRole(null);

        // when
        var exception = assertThrows(
                AccountAlreadyExistException.class,
                () -> authService.register(email, password, nickname)
        );

        // then
        assertEquals("Аккаунт с почтой: " + email + " уже существует", exception.getMessage());
    }

    @DisplayName("Load user by user name")
    @ParameterizedTest(name = "{index} - load user by user name {0}")
    @ValueSource(strings = {"dan@dan.ru", "daad@dddd.com"})
    void loadUserByUsernameTest(String email) throws RoleNotFoundException {
        // given
        var accountGiven = new Account(null, email, "123", "asdasda", null, null, null, null, null, false, roleService.getRoleByName(Role.ROLE_USER), null, null, null);
        accountRepository.save(accountGiven);

        // when
        var accountWhen = accountUserDetailsService.loadUserByUsername(email);

        // then
        var account = accountRepository.findById(1L);
        assertTrue(account.isPresent());
        var accountGet = account.get();
        assertEquals(accountWhen.getUsername(), accountGet.getUsername());
    }

    @DisplayName("Register admin")
    @Test
    void registerAdminTest() throws RoleNotFoundException {
        // given

        // when
        authService.registerAdmin(new Account(null, "admin@admin.ru", "admin", "admin", "example", "example", "example", null, null, false, roleService.getRoleByName(Role.ROLE_ADMIN), null, null, null));

        // then
        var accounts = accountRepository.findAll();
        assertEquals(1, accounts.size());
        var account = accounts.get(0);
        assertEquals(1, account.getId());
        assertEquals("admin@admin.ru", account.getEmail());
        assertEquals("admin", account.getNickname());
    }
}


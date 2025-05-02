package edu.tinkoff.tinkoffbackendacademypetproject.service;

import edu.tinkoff.tinkoffbackendacademypetproject.CommonAbstractTest;
import edu.tinkoff.tinkoffbackendacademypetproject.exceptions.EntityModelNotFoundException;
import edu.tinkoff.tinkoffbackendacademypetproject.exceptions.RoleNotFoundException;
import edu.tinkoff.tinkoffbackendacademypetproject.model.Account;
import edu.tinkoff.tinkoffbackendacademypetproject.model.Role;
import edu.tinkoff.tinkoffbackendacademypetproject.model.RoleEntity;
import edu.tinkoff.tinkoffbackendacademypetproject.repositories.AccountRepository;
import edu.tinkoff.tinkoffbackendacademypetproject.repositories.RoleRepository;
import edu.tinkoff.tinkoffbackendacademypetproject.services.AccountService;
import edu.tinkoff.tinkoffbackendacademypetproject.services.RoleService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.Charset;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest extends CommonAbstractTest {
    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    @AfterEach
    void clear() {
        accountRepository.deleteAll();
        jdbcTemplate.execute("ALTER SEQUENCE account_pk_seq RESTART");
        roleRepository.deleteAll();
        jdbcTemplate.execute("ALTER SEQUENCE role_pk_seq RESTART");
    }

    @DisplayName("Get all users")
    @ParameterizedTest(name = "{index} - find all users")
    @CsvSource(value = {
            "0, 50",
    })
    void getAllUsersTest(Integer pageNumber, Integer pageSize) throws RoleNotFoundException {
        // given
        createBeforeStart();

        // when
        var usersWhen = accountService.getAllUsers(pageNumber, pageSize);

        // then
        var users = accountRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by("id")));
        assertEquals(usersWhen.getSize(), users.getSize());
        for (int i = 0; i < users.getContent().size(); i++) {
            assertEquals(users.getContent().get(i).getId(), usersWhen.getContent().get(i).getId());
            assertEquals(users.getContent().get(i).getNickname(), usersWhen.getContent().get(i).getNickname());
        }
    }

    @DisplayName("Ban user")
    @ParameterizedTest(name = "{index} - ban user {0}")
    @ValueSource(longs = {1, 2})
    void banUserByIdTest(Long id) {
        // given
        createBeforeStart();

        // when
        var userWhen = accountService.banUserById(id);

        // then
        var user = accountRepository.findById(id);
        assertTrue(user.isPresent());
        var userGet = user.get();
        assertEquals(userWhen.getId(), userGet.getId());
        assertEquals(userWhen.getIsBanned(), userGet.getIsBanned());
        assertEquals(userWhen.getNickname(), userGet.getNickname());
    }

    @DisplayName("Unban user")
    @ParameterizedTest(name = "{index} - unban user {0}")
    @ValueSource(longs = {1, 2})
    void unbanUserByIdTest(Long id) {
        // given
        createBeforeStart();

        // when
        var userWhen = accountService.unbanUserById(id);

        // then
        var user = accountRepository.findById(id);
        assertTrue(user.isPresent());
        var userGet = user.get();
        assertEquals(userWhen.getId(), userGet.getId());
        assertEquals(userWhen.getIsBanned(), userGet.getIsBanned());
        assertEquals(userWhen.getNickname(), userGet.getNickname());
    }

    @DisplayName("Get account")
    @ParameterizedTest(name = "{index} - account {0} is find")
    @ValueSource(strings = {"Daniil K", "User"})
    void getAccountTest(String nickname) throws EntityModelNotFoundException {
        // given
        createBeforeStart();

        // when
        var accountWhen = accountService.getAccount(nickname);

        // then
        var account = accountRepository.findByNickname(nickname);
        assertTrue(account.isPresent());
        var accountGet = account.get();
        assertEquals(accountWhen.getId(), accountGet.getId());
        assertEquals(accountWhen.getNickname(), accountGet.getNickname());
        assertEquals(accountWhen.getEmail(), accountGet.getEmail());
    }

    @DisplayName("Get account with throw")
    @ParameterizedTest(name = "{index} - account {0} is not find")
    @ValueSource(strings = {"Daniil K", "User"})
    void getAccountWithThrowTest(String nickname) {
        // given

        // when
        var exception = assertThrows(
                EntityModelNotFoundException.class,
                () -> accountService.getAccount(nickname)
        );

        // then
        assertEquals("Пользователя с nickname: " + nickname + " не найдено", exception.getMessage());
    }

    @DisplayName("Get account by email")
    @ParameterizedTest(name = "{index} - account {0} is find")
    @ValueSource(strings = {"dan@dam.ru", "user@user.ru"})
    void getAccountByEmailTest(String email) throws EntityModelNotFoundException {
        // given
        createBeforeStart();

        // when
        var accountWhen = accountService.getAccountByEmail(email);

        // then
        var account = accountRepository.findByEmail(email);
        assertTrue(account.isPresent());
        var accountGet = account.get();
        assertEquals(accountWhen.getId(), accountGet.getId());
        assertEquals(accountWhen.getNickname(), accountGet.getNickname());
        assertEquals(accountWhen.getEmail(), accountGet.getEmail());
    }

    @DisplayName("Get account by email with throw")
    @ParameterizedTest(name = "{index} - account {0} is not find")
    @ValueSource(strings = {"dan@dam.ru", "user@user.ru"})
    void getAccountByEmailWithThrowTest(String email) {
        // given

        // when
        var exception = assertThrows(
                EntityModelNotFoundException.class,
                () -> accountService.getAccountByEmail(email)
        );

        // then
        assertEquals("Пользователя с почтой: " + email + " не найдено", exception.getMessage());
    }

    @DisplayName("Update account role")
    @ParameterizedTest(name = "{index} - account role {0} is updated")
    @CsvSource(value = {
            "1, 2",
            "2, 2"
    })
    void updateAccountRoleTest(Long accountId, Long roleId) throws RoleNotFoundException {
        // given
        createBeforeStart();

        // when
        var accountWhen = accountService.updateAccountRole(accountId, roleId);

        // then
        var account = accountRepository.findById(accountId);
        assertTrue(account.isPresent());
        var accountGet = account.get();
        assertEquals(accountWhen.getId(), accountGet.getId());
        assertEquals(accountWhen.getNickname(), accountGet.getNickname());
        assertEquals(accountWhen.getEmail(), accountGet.getEmail());
        assertEquals(accountWhen.getRole().getName(), accountGet.getRole().getName());
    }

    @DisplayName("Save information about account")
    @ParameterizedTest(name = "{index} - save information")
    @CsvSource(value = {
            "User, я повар, Даниил, Коршунов, Андреевич, 2000-01-01"
    })
    @WithMockUser(username = "user@user.ru")
    void saveInformationAboutAccountTest(String nickname, String description, String firstName,
                                         String lastName, String middleName, String birthDate) throws EntityModelNotFoundException {
        // given
        createBeforeStart();
        var accountGiven = new Account(null, null, null, nickname, description, firstName, lastName, middleName, LocalDate.parse(birthDate), null, null, null, null, null);

        MultipartFile fileA = new MockMultipartFile(
                "test.txt",
                "test.txt",
                "text/plain",
                "Lorem ipsum dolores".getBytes(Charset.defaultCharset())
        );
        // when
        accountService.saveInformationAboutAccount(accountGiven, fileA);

        // then
        var account = accountRepository.findById(2L);
        assertTrue(account.isPresent());
        var accountGet = account.get();
        assertEquals(nickname, accountGet.getNickname());
        assertEquals(description, accountGet.getDescription());
        assertEquals(firstName, accountGet.getFirstName());
        assertEquals(lastName, accountGet.getLastName());
        assertEquals(middleName, accountGet.getMiddleName());
        assertEquals(LocalDate.parse(birthDate), accountGet.getBirthDate());
    }

    private void createBeforeStart() throws RoleNotFoundException {
        insertRoles();
        accountRepository.save(new Account(null, "dan@dam.ru", "1234", "Daniil K", null, null, null, null, null, false, roleService.getRoleByName(Role.ROLE_USER), null, null, null));
        accountRepository.save(new Account(null, "user@user.ru", "12345", "User", null, null, null, null, null, false, roleService.getRoleByName(Role.ROLE_USER), null, null, null));
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


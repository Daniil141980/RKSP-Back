package edu.tinkoff.tinkoffbackendacademypetproject.config;

import edu.tinkoff.tinkoffbackendacademypetproject.exceptions.RoleNotFoundException;
import edu.tinkoff.tinkoffbackendacademypetproject.model.*;
import edu.tinkoff.tinkoffbackendacademypetproject.services.AuthService;
import edu.tinkoff.tinkoffbackendacademypetproject.services.RoleService;
import edu.tinkoff.tinkoffbackendacademypetproject.services.TopicTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

    private final AuthService authService;
    private final RoleService roleService;
    private final TopicTypeService topicTypeService;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String password;

    @Value("${admin.nickname}")
    private String nickname;

    @Override
    public void run(ApplicationArguments args) throws RoleNotFoundException {
        insertRoles();
        insertTopicTypes();
        registerAdmin();
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

    private void insertTopicTypes() {
        Topic[] topics = Topic.values();
        for (Topic topic : topics) {
            if (!topicTypeService.topicExists(topic)) {
                TopicTypeEntity type = new TopicTypeEntity(null, topic, null);
                topicTypeService.save(type);
            }
        }
    }

    private void registerAdmin() {
        authService.registerAdmin(
                new Account(null,
                        adminEmail,
                        password,
                        nickname,
                        null,
                        null,
                        null,
                        null,
                        LocalDate.now(),
                        false,
                        roleService.getRoleByName(Role.ROLE_ADMIN),
                        null,
                        null,
                        null));
    }

}
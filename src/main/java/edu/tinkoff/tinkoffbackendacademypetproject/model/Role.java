package edu.tinkoff.tinkoffbackendacademypetproject.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ROLE_ADMIN("Администратор"),
    ROLE_MODERATOR("Модератор"),
    ROLE_USER("Пользователь");
    private final String description;
}

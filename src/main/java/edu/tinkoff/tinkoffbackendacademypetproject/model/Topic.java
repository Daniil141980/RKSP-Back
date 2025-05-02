package edu.tinkoff.tinkoffbackendacademypetproject.model;

import lombok.Getter;

/**
 * Виды топиков
 */
@Getter
public enum Topic {
    TEST("Контрольная работа"),
    SEMINAR_NOTES("Конспекты семинара"),
    LITERATURE("Литература"),
    EXAM("Экзамен");

    /**
     * Описание топика
     */
    private final String description;

    /**
     * Конструктор с указанием описания топика
     *
     * @param description описание топика
     */
    Topic(String description) {
        this.description = description;
    }
}

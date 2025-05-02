package edu.tinkoff.tinkoffbackendacademypetproject.exceptions;

/**
 * Ошибка, которая уведомляет, что предмет уже существует
 */
public class SubjectAlreadyExistsException extends AlreadyExistsException {
    /**
     * Принимает сообщение ошибки
     *
     * @param name сообщение ошибки
     */
    public SubjectAlreadyExistsException(String name) {
        super("Предмет с названием " + name + " уже существует");
    }
}

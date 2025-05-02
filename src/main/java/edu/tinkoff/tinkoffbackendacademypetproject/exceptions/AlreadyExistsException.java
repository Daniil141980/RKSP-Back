package edu.tinkoff.tinkoffbackendacademypetproject.exceptions;

/**
 * Абстрактная ошибка, говорящая, что что-то уже существует
 */
public abstract class AlreadyExistsException extends RuntimeException {
    /**
     * Принимает сообщение ошибки
     *
     * @param message сообщение ошибки
     */
    protected AlreadyExistsException(String message) {
        super(message);
    }
}

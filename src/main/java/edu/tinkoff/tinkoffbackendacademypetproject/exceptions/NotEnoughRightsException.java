package edu.tinkoff.tinkoffbackendacademypetproject.exceptions;

public class NotEnoughRightsException extends RuntimeException {
    public NotEnoughRightsException() {
        super("У вас недостаточно прав для выполнения данного действия");
    }

    public NotEnoughRightsException(String message) {
        super(message);
    }
}

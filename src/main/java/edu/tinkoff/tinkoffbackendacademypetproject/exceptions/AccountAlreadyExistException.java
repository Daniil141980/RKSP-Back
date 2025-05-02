package edu.tinkoff.tinkoffbackendacademypetproject.exceptions;

public class AccountAlreadyExistException extends AlreadyExistsException {
    public AccountAlreadyExistException(String type, String email) {
        super("Аккаунт с " + type + ": " + email + " уже существует");
    }
}

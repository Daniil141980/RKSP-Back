package edu.tinkoff.tinkoffbackendacademypetproject.exceptions;

public class BannedAccountException extends RuntimeException {
    public BannedAccountException() {
        super("Ваш аккаунт был удален");
    }
}

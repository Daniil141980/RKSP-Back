package edu.tinkoff.tinkoffbackendacademypetproject.exceptions;

public class DepthThreadException extends RuntimeException {
    public DepthThreadException() {
        super("Тред слишком глубокий, нельзя его создать");
    }
}

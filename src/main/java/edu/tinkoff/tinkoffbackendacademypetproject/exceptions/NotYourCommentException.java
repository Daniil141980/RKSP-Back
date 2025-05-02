package edu.tinkoff.tinkoffbackendacademypetproject.exceptions;

public class NotYourCommentException extends NotEnoughRightsException {
    public NotYourCommentException() {
        super("Нельзя изменять чужой комментарий");
    }
}

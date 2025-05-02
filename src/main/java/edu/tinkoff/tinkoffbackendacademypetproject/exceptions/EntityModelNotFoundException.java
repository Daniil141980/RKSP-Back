package edu.tinkoff.tinkoffbackendacademypetproject.exceptions;

public class EntityModelNotFoundException extends RuntimeException {

    public EntityModelNotFoundException(String entity, String field, String valueField) {
        super(entity + " с " + field + ": " + valueField + " не найдено");
    }
}
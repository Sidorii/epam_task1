package ua.epam.trainee.model.exceptions;

public class MissingEntityException extends RuntimeException {

    private Object entity;


    public MissingEntityException(Object entity, String message) {
        super(message);
        this.entity = entity;
    }

    public MissingEntityException(Object entity) {
        this.entity = entity;
    }

    public MissingEntityException(String message) {
        super(message);
    }

    public Object getEntity() {
        return entity;
    }
}

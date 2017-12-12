package ua.epam.trainee.model.exceptions;

public class DuplicatedEntryException extends RuntimeException {

    private Object entry;

    public DuplicatedEntryException(Object entry) {
        super("Entry already exists");
        this.entry = entry;
    }

    public DuplicatedEntryException(String message, Object entry) {
        super(message);
        this.entry = entry;
    }

    public Object getEntry() {
        return entry;
    }
}

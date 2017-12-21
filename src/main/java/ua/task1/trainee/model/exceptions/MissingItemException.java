package ua.task1.trainee.model.exceptions;

import ua.task1.trainee.model.entities.Item;

public class MissingItemException extends MissingEntityException{

    private String name;

    public MissingItemException(Item item) {
        super(item, "Required item is missed: " + item.getName());
        name = item.getName();
    }

    public MissingItemException(String message) {
        super("No item found: " + message);
    }

    @Override
    public Item getEntity() {
        return (Item) super.getEntity();
    }

    public MissingItemException(Item item, String message) {
        super(item,message);
    }

    public String getName() {
        return name;
    }
}

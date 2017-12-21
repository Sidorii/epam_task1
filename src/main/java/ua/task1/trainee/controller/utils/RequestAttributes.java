package ua.task1.trainee.controller.utils;

public final class RequestAttributes {

    public static final String AUTHENTICATION = "auth";
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String CONFIRM_PASSWORD = "confirm-password";
    public static final String ROLES = "roles";
    public static final String LANG = "lang";
    public static final String REDIRECT_URL = "redirect";
    public static final String INVALID = "invalid";
    public static final String TYPES = "types";
    public static final String ACTION = "action";
    public static final String TITLE = "title";
    public static final String ID = "id";

    public static final class IngredientAttributes{
        public static final String INGREDIENT = "ingredient";
        public static final String INGREDIENTS = "ingredients";
        public static final String NAME = "name";
        public static final String WEIGHT = "weight";
        public static final String CALORIES = "calories";
        public static final String PRICE = "price";
        public static final String IS_FRESH = "fresh";
        public static final String DESC = "description";
        public static final String TYPE = "type";
    }

    public static final class SaladAttributes{
        public static final String SALAD = "salad";
        public static final String NAME = "name";
        public static final String DISHES = "dishes";
    }
}

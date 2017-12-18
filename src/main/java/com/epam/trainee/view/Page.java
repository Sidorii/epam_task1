package com.epam.trainee.view;

public enum Page implements View {

    ALL_SALADS("newSalads"),
    ALL_INGREDIENTS("ingredients"),
    MISSED_ENTITY("missedEntity"),
    NOT_FOUND("notFound"),
    SINGLE_SALAD("salad"),
    SINGLE_INGREDIENT("singleIngredient"),
    CREATE_SALAD("createSalad"),
    CREATE_INGREDIENT("createIngredient"),
    DUPLICATED_ENTRY("duplicatedEntry"),
    HOME("home");

    private static final String prefix = "/WEB-INF/";
    private static final String suffix = ".jsp";

    private String viewName;

    Page(String viewName) {
        this.viewName = viewName;
    }

    @Override
    public String getView() {
        return prefix.concat(viewName).concat(suffix);
    }

    public static Page from(String viewName) {
        for (Page page : values()) {
            if (page.viewName.equals(viewName)) {
                return page;
            }
        }
        return Page.NOT_FOUND;
    }
}

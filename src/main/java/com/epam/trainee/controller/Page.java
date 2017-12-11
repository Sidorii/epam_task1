package com.epam.trainee.controller;

public enum Page {

    ALL_SALADS("salads"),
    ALL_INGREDIENTS("ingredients"),
    MISSED_ENTITY("missedEntity"),
    NOT_FOUND("notFound"),
    SINGLE_SALAD("salad");

    private static final String prefix = "/WEB-INF/";
    private static final String suffix = ".jsp";

    private String viewName;

    Page(String viewName) {
        this.viewName = viewName;
    }

    public String getView() {
        return prefix.concat(viewName).concat(suffix);
    }
}

package com.epam.trainee.controller.utils.validators;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

public abstract class NotNullOrEmptyValidator implements Validator {

    protected Set<String> invalidParams;
    protected HttpServletRequest req;

    public NotNullOrEmptyValidator(HttpServletRequest request) {
        this.req = request;
        this.invalidParams = new HashSet<>();
    }

    protected void checkNullOrEmpty(String key) {
        String param = req.getParameter(key);
        if (param == null || param.equals("")) {
            invalidParams.add(key);
        }
    }
}

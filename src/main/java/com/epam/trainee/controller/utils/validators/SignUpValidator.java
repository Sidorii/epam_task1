package com.epam.trainee.controller.utils.validators;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

import static com.epam.trainee.controller.utils.RequestAttributes.*;

public class SignUpValidator implements Validator {

    public static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*" +
            "@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private final Set<String> invalidParams;
    private final HttpServletRequest req;

    public SignUpValidator(HttpServletRequest request) {
        req = request;
        invalidParams = new HashSet<>();
    }

    @Override
    public Set<String> validate() {
        invalidParams.clear();

        checkNullOrEmpty(NAME);
        checkNullOrEmpty(EMAIL);
        checkNullOrEmpty(PASSWORD);
        checkNullOrEmpty(CONFIRM_PASSWORD);

        if (!req.getParameter(EMAIL).matches(EMAIL_REGEX)) {
            invalidParams.add(EMAIL);
        }

        if (!req.getParameter(PASSWORD).equals(req.getParameter(CONFIRM_PASSWORD))) {
            invalidParams.add(CONFIRM_PASSWORD);
        }
        return invalidParams;
    }

    private void checkNullOrEmpty(String key) {
        String param = req.getParameter(key);
        if (param == null || param.equals("")) {
            invalidParams.add(key);
        }
    }
}

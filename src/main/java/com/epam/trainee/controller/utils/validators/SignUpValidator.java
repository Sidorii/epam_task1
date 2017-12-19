package com.epam.trainee.controller.utils.validators;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

import static com.epam.trainee.controller.utils.RequestAttributes.*;

public class SignUpValidator extends NotNullOrEmptyValidator {

    public static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*" +
            "@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public SignUpValidator(HttpServletRequest request) {
        super(request);
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

        String roles[] = req.getParameterValues(ROLES);
        if (roles == null || roles.length == 0) {
            invalidParams.add(ROLES);
        }

        if (!req.getParameter(PASSWORD).equals(req.getParameter(CONFIRM_PASSWORD))) {
            invalidParams.add(CONFIRM_PASSWORD);
        }
        return invalidParams;
    }
}

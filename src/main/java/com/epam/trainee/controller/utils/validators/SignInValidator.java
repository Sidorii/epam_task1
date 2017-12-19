package com.epam.trainee.controller.utils.validators;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

import static com.epam.trainee.controller.utils.RequestAttributes.*;

public class SignInValidator extends NotNullOrEmptyValidator {

    public static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*" +
            "@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    public SignInValidator(HttpServletRequest request) {
        super(request);
    }

    @Override
    public Set<String> validate() {
        checkNullOrEmpty(EMAIL);
        checkNullOrEmpty(PASSWORD);

        if (!req.getParameter(EMAIL).matches(EMAIL_REGEX)) {
            invalidParams.add(EMAIL);
        }
        return invalidParams;
    }
}

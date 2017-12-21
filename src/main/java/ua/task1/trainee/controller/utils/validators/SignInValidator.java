package ua.task1.trainee.controller.utils.validators;

import ua.task1.trainee.controller.utils.RequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

public class SignInValidator extends NotNullOrEmptyValidator {

    public static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*" +
            "@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    public SignInValidator(HttpServletRequest request) {
        super(request);
    }

    @Override
    public Set<String> validate() {
        checkNullOrEmpty(RequestAttributes.EMAIL);
        checkNullOrEmpty(RequestAttributes.PASSWORD);

        if (!req.getParameter(RequestAttributes.EMAIL).matches(EMAIL_REGEX)) {
            invalidParams.add(RequestAttributes.EMAIL);
        }
        return invalidParams;
    }
}

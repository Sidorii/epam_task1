package ua.task1.trainee.controller.utils.validators;

import ua.task1.trainee.controller.utils.RequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

public class SignUpValidator extends NotNullOrEmptyValidator {

    public static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*" +
            "@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public SignUpValidator(HttpServletRequest request) {
        super(request);
    }

    @Override
    public Set<String> validate() {
        invalidParams.clear();

        checkNullOrEmpty(RequestAttributes.NAME);
        checkNullOrEmpty(RequestAttributes.EMAIL);
        checkNullOrEmpty(RequestAttributes.PASSWORD);
        checkNullOrEmpty(RequestAttributes.CONFIRM_PASSWORD);

        if (!req.getParameter(RequestAttributes.EMAIL).matches(EMAIL_REGEX)) {
            invalidParams.add(RequestAttributes.EMAIL);
        }

        String roles[] = req.getParameterValues(RequestAttributes.ROLES);
        if (roles == null || roles.length == 0) {
            invalidParams.add(RequestAttributes.ROLES);
        }

        if (!req.getParameter(RequestAttributes.PASSWORD).equals(req.getParameter(RequestAttributes.CONFIRM_PASSWORD))) {
            invalidParams.add(RequestAttributes.CONFIRM_PASSWORD);
        }
        return invalidParams;
    }
}

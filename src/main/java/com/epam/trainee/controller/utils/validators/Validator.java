package com.epam.trainee.controller.utils.validators;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

public interface Validator {

    Set<String> validate();
}

package com.epam.trainee.controller.utils;


import com.epam.trainee.model.entities.Role;
import com.epam.trainee.model.entities.User;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.StringWriter;

import static com.epam.trainee.controller.utils.RequestAttributes.AUTHENTICATION;

public class JspUserHasRoleTag extends TagSupport {

    private Role role;
    private StringWriter sw;


    public void setRole(String role) {
        this.role = Role.valueOf(role);
        sw = new StringWriter();
    }

    @Override
    public int doStartTag() throws JspException {
        HttpSession session = pageContext.getSession();
        if (session == null) {
            return SKIP_BODY;
        }

        User user = (User) session.getAttribute(AUTHENTICATION);

        if (user != null && user.getRoles().contains(role)) {
            return EVAL_BODY_INCLUDE;
        }
        return SKIP_BODY;
    }
}

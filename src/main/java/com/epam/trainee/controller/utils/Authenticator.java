package com.epam.trainee.controller.utils;

import com.epam.trainee.model.entities.Role;
import com.epam.trainee.model.entities.User;
import com.epam.trainee.view.Page;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;

import static com.epam.trainee.controller.utils.RequestAttributes.AUTHENTICATION;

public class Authenticator {

    private final Role ROLE;

    public Authenticator(Role role) {
        this.ROLE = role;
    }

    public void process(HttpServletRequest req,
                        HttpServletResponse resp,
                        FilterChain chain) throws IOException, ServletException {
        HttpSession session = req.getSession(false);
        if (hasAccess(session)) {
            chain.doFilter(req, resp);
        } else {
            req.setAttribute("bundle", ResourceBundle.getBundle("MessageBundle"));
            req.setAttribute("status", "Permission denied. Role " + ROLE + " required");
            req.getRequestDispatcher(Page.LOGIN.getView()).forward(req,resp);
        }
    }

    private boolean hasAccess(HttpSession session) {
        if (Objects.isNull(session)) {
            return false;
        }

        @SuppressWarnings("unchecked")
        Set<Role> actualRole = ((User) session.getAttribute(AUTHENTICATION)).getRoles();
        return actualRole != null && actualRole.contains(ROLE);
    }
}

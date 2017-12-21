package ua.task1.trainee.controller.utils;

import ua.task1.trainee.model.entities.Role;
import ua.task1.trainee.model.entities.User;
import ua.task1.trainee.view.Page;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;

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
            req.setAttribute("status", "Permission denied. Role " + ROLE + " required");
            req.getRequestDispatcher(Page.LOGIN.getView()).forward(req,resp);
        }
    }

    private boolean hasAccess(HttpSession session) {
        if (Objects.isNull(session)) {
            return false;
        }
        Object roles = session.getAttribute(RequestAttributes.AUTHENTICATION);
        if (roles == null) {
            return false;
        }
        Set<Role> actualRole = ((User) roles).getRoles();
        return actualRole != null && actualRole.contains(ROLE);
    }
}

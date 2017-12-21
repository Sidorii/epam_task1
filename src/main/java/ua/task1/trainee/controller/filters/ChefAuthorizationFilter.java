package ua.task1.trainee.controller.filters;

import ua.task1.trainee.controller.utils.Authenticator;
import ua.task1.trainee.model.entities.Role;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/chef/*")
public class ChefAuthorizationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        new Authenticator(Role.CHEF).process(req,resp,chain);
    }

    @Override
    public void destroy() {}
}

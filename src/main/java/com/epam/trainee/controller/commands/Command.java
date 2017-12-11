package com.epam.trainee.controller.commands;

import com.epam.trainee.controller.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {

    Page execute(HttpServletRequest req, HttpServletResponse resp);
}

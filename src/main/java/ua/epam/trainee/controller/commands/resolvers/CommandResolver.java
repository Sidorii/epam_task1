package ua.epam.trainee.controller.commands.resolvers;

import ua.epam.trainee.controller.commands.Command;

import javax.servlet.http.HttpServletRequest;

public abstract class CommandResolver {

    private static CommandResolver instance;

    public static CommandResolver getInstance() {
        if (instance == null) {
            synchronized (CommandResolver.class) {
                if (instance == null) {
                    instance = new CommandResolverImpl();
                }
            }
        }
        return instance;
    }

    public abstract Command resolveCommand(HttpServletRequest req);
}

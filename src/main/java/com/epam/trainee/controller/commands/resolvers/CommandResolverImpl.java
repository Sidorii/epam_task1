package com.epam.trainee.controller.commands.resolvers;

import com.epam.trainee.controller.commands.Command;
import com.epam.trainee.controller.commands.NotFoundCommand;
import com.epam.trainee.controller.utils.WebUrl;
import com.epam.trainee.model.exceptions.MissingEntityException;
import org.reflections.Reflections;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CommandResolverImpl extends CommandResolver {
    
    private static Map<String, Class<? extends Command>> commandsUrl = new HashMap<>();

    static {
        Function<? super Class<?>, ? extends String> keyMapper = (clazz) ->
        {
            String url = trimSlashes(clazz.getAnnotation(WebUrl.class).value());
            if (commandsUrl.containsKey(url)) {
                throw new IllegalStateException("Duplication url: " + url);
            }
            return url;
        };

        Reflections reflection = new Reflections("com.epam.trainee");
        Set<Class<?>> classes = reflection.getTypesAnnotatedWith(WebUrl.class);
        commandsUrl = classes.stream().filter(clazz -> !(classes instanceof Command))
                .collect(Collectors.toMap(keyMapper, clazz -> (Class<? extends Command>) clazz));
    }

    @Override
    public Command resolveCommand(HttpServletRequest req) {
        System.out.println(req.getServletPath());
        String subRequest = trimSlashes(req.getServletPath());
        try {
            return commandsUrl.getOrDefault(subRequest, NotFoundCommand.class).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new MissingEntityException(req.getRequestURI(), "Request handler not found");
        }
    }

    private static String trimSlashes(String url) {
        if (!url.isEmpty() && isEndOnSlash(url)) {
            url = url.substring(0, url.length() - 1);
        }

        if (!url.isEmpty() && isStartWithSlash(url)) {
            url = url.substring(1, url.length());
        }
        return url;
    }

    private static boolean isEndOnSlash(String url) {
        return url.charAt(url.length() - 1) == '/';
    }

    private static boolean isStartWithSlash(String url) {
        return url.charAt(0) == '/';
    }
}

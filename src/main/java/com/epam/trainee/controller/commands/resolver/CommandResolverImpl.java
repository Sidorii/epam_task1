package com.epam.trainee.controller.commands.resolver;

import com.epam.trainee.controller.commands.Command;
import com.epam.trainee.controller.commands.NotFoundCommand;
import com.epam.trainee.controller.commands.WebUrl;
import com.epam.trainee.model.exceptions.MissingEntityException;
import org.reflections.Reflections;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CommandResolverImpl extends CommandResolver {

    private static final Matcher baseBodyMatcher = Pattern
            .compile("^(http[s]?://[a-zA-ZА-Яа-я[ЇїІієЄ]0-9-:_]+/)")
            .matcher("");

    private static Map<String, Class<? extends Command>> commandsUrl = new HashMap<>();

    static {
        Function<? super Class<?>, ? extends String> keyMapper = (clazz) ->
        {
            String url = clazz.getAnnotation(WebUrl.class).value();
            if (commandsUrl.containsKey(url)) {
                throw new IllegalStateException("Command with url [" + url + "] already exist");
            }
            return url;
        };

        Reflections reflection = new Reflections();
        Set<Class<?>> classes = reflection.getTypesAnnotatedWith(WebUrl.class);
        commandsUrl = classes.stream().filter(clazz -> !(classes instanceof Command))
                .collect(Collectors.toMap(keyMapper, clazz -> (Class<? extends Command>) clazz));
    }

    @Override
    public Command resolveCommand(HttpServletRequest req) {
        StringBuffer request = req.getRequestURL();
        String subRequest = prepareRequest(request);
        try {
            return commandsUrl.getOrDefault(subRequest, NotFoundCommand.class).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new MissingEntityException(request, "Request handler not found");
        }
    }

    private String prepareRequest(StringBuffer request) {
        String subRequest = baseBodyMatcher.reset(request).replaceFirst("");

        if (!subRequest.isEmpty() && isEndOnSlash(subRequest)) {
            subRequest = subRequest.substring(0, subRequest.length() - 1);
        }
        return subRequest;
    }

    private boolean isEndOnSlash(String subRequest) {
        return subRequest.charAt(subRequest.length()-1) == '/';
    }
}

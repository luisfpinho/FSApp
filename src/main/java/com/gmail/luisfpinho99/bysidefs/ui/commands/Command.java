package com.gmail.luisfpinho99.bysidefs.ui.commands;

import com.gmail.luisfpinho99.bysidefs.domain.model.entry.Directory;
import com.gmail.luisfpinho99.bysidefs.domain.model.user.User;
import com.gmail.luisfpinho99.bysidefs.ui.Terminal;

import java.util.*;
import java.util.stream.Collectors;

public abstract class Command {

    protected final Terminal terminal;
    private final String name;
    private final Map<String, Boolean> acceptedParameters;

    private static final char COMMAND_IDENTIFIER = '-';

    public Command(Terminal terminal, String name, Map<String, Boolean> acceptedParameters) {
        this.terminal = terminal;
        this.name = name;
        this.acceptedParameters = new HashMap<>(acceptedParameters);
    }

    public Command(Terminal terminal, String name) {
        this(terminal, name, new HashMap<>());
    }

    public void execute(User user, Directory currentDirectory, String rawArgs) {
        List<String> args = new ArrayList<>();
        Set<CommandParameter> params = new HashSet<>();
        parseArgsParams(rawArgs, args, params);
        run(user, currentDirectory, args, params);
    }

    public boolean matches(String command) {
        if (command == null || command.isEmpty()) {
            return false;
        }

        return command.split(" ")[0].equalsIgnoreCase(name);
    }

    public String help() {
        return String.format("%-6s %s", name, acceptedParamsAsString());
    }

    protected abstract void run(User user, Directory currentDirectory, List<String> args, Set<CommandParameter> parameters);

    protected static String joinArgs(List<String> args) {
        return String.join(" ", args);
    }

    protected static boolean containsParameter(Set<CommandParameter> parameters, String parameter) {
        for (CommandParameter commandParameter : parameters) {
            if (commandParameter.parameter.equalsIgnoreCase(parameter)) {
                return true;
            }
        }

        return false;
    }

    private String acceptedParamsAsString() {
        List<String> list = acceptedParameters.keySet().stream()
                .map(param -> COMMAND_IDENTIFIER + param)
                .sorted()
                .collect(Collectors.toList());

        StringBuilder result = new StringBuilder();
        for (String param : list) {
            result.append(param).append(' ');
        }

        return result.toString();
    }

    private void parseArgsParams(String rawArgs, List<String> args, Set<CommandParameter> params) {
        rawArgs = rawArgs.trim();
        String[] parts = rawArgs.split(" ");

        for (int i = 0; i < parts.length; i++) {
            String part = parts[i].trim();
            if (part.isEmpty()) {
                continue;
            }

            // If a parameter was found and it has a value, skip the next part
            if (part.charAt(0) == COMMAND_IDENTIFIER && part.length() > 1 && parseParam(parts, i, params)) {
                if (parseParam(parts, i, params)) {
                    i++;
                }
            } else {
                args.add(part);
            }
        }
    }

    private boolean parseParam(String[] parts, int index, Set<CommandParameter> params) {
        String param = parts[index].substring(1);

        Boolean acceptsValue = acceptedParameters.get(param);
        if (acceptsValue == null) {
            throw new IllegalArgumentException(
                    String.format("%s: The parameter '%s' is unrecognized.", name, param)
            );
        }

        if (acceptsValue && index >= parts.length - 1) {
            throw new IllegalArgumentException(String.format("%s: The parameter '%s' required a value.", name, param));
        }

        if (acceptsValue) {
            CommandParameter parameter = new CommandParameter(param, parts[index + 1]);
            params.add(parameter);
            return true;
        } else {
            CommandParameter parameter = new CommandParameter(param);
            params.add(parameter);
            return false;
        }
    }
}

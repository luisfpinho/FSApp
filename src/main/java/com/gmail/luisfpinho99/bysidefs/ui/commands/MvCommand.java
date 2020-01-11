package com.gmail.luisfpinho99.bysidefs.ui.commands;

import com.gmail.luisfpinho99.bysidefs.domain.model.entry.Directory;
import com.gmail.luisfpinho99.bysidefs.domain.model.user.User;
import com.gmail.luisfpinho99.bysidefs.domain.service.MoveEntryService;
import com.gmail.luisfpinho99.bysidefs.ui.Terminal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MvCommand extends Command {

    private static final Map<String, Boolean> ACCEPTED_PARAMETERS = createAcceptedParameters();

    private static final String KEEP_PERMISSIONS_PARAM = "-e";
    private static final String KEEP_PERMISSIONS_PARAM_FULL = "-keep-permissions";
    private static final String KEEP_OWNER_PARAM = "-o";
    private static final String KEEP_OWNER_PARAM_FULL = "-keep-owner";

    public MvCommand(Terminal terminal) {
        super(terminal, "mv", ACCEPTED_PARAMETERS);
    }

    @Override
    protected void run(User user, Directory currentDirectory, List<String> args, Set<CommandParameter> parameters) {
        if (args.size() != 2) {
            throw new IllegalArgumentException("The move command takes 2 arguments: the origin path and the destination path.");
        }

        boolean keepPermissions = containsParameter(parameters, KEEP_PERMISSIONS_PARAM) || containsParameter(parameters, KEEP_PERMISSIONS_PARAM_FULL);
        boolean keepOwner = containsParameter(parameters, KEEP_OWNER_PARAM) || containsParameter(parameters, KEEP_OWNER_PARAM_FULL);

        MoveEntryService moveEntryService = new MoveEntryService();
        moveEntryService.moveEntryService(user, currentDirectory, args.get(0), args.get(1), keepOwner, keepPermissions);

        System.out.printf("The file '%s' was moved to '%s'.", args.get(0), args.get(1));
    }

    private static Map<String, Boolean> createAcceptedParameters() {
        Map<String, Boolean> map = new HashMap<>();
        map.put(KEEP_PERMISSIONS_PARAM, false);
        map.put(KEEP_PERMISSIONS_PARAM_FULL, false);
        map.put(KEEP_OWNER_PARAM, false);
        map.put(KEEP_OWNER_PARAM_FULL, false);
        return map;
    }
}

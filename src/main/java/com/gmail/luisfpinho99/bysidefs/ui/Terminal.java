package com.gmail.luisfpinho99.bysidefs.ui;

import com.gmail.luisfpinho99.bysidefs.domain.model.entry.Directory;
import com.gmail.luisfpinho99.bysidefs.domain.service.FindEntryService;
import com.gmail.luisfpinho99.bysidefs.domain.service.UpdateCurrentDirectoryService;
import com.gmail.luisfpinho99.bysidefs.ui.commands.*;

import java.util.HashSet;
import java.util.Set;

public class Terminal extends UI {

    private Directory currentDirectory;
    public final Set<Command> commands;

    private static final FindEntryService findEntryService = new FindEntryService();

    public static final String EXIT_COMMAND = "exit";

    public Terminal() {
        super();
        commands = createCommands();
    }

    public void start() {
        loginScreen();
    }

    public void changeCurrentDirectory(Directory currentDirectory) {
        this.currentDirectory = currentDirectory;
    }

    private void loginScreen() {
        System.out.println("\n-------------------------");
        System.out.println("Welcome to FSApp Terminal");
        System.out.println("-------------------------");

        LoginUI loginUI = new LoginUI();
        loginUI.login();

        this.loggedInUser = loginUI.loggedInUser;
        this.currentDirectory = findRoot();
        askCommands();
    }

    private void askCommands() {
        UpdateCurrentDirectoryService updateCurrentDirectoryService = new UpdateCurrentDirectoryService();
        String rawCommand = "";

        while (true) {
            System.out.printf("%s > ", currentDirectory.absolutePath());
            rawCommand = scanner.nextLine();
            if (rawCommand.equalsIgnoreCase(EXIT_COMMAND)) {
                break;
            }

            try {
                runCommand(rawCommand);
            } catch (IllegalArgumentException e) {
                System.out.printf("Error: %s%n", e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }

            currentDirectory = updateCurrentDirectoryService.updateCurrentDirectory(loggedInUser, currentDirectory);
        }

        loginScreen();
    }

    private void runCommand(String rawCommand) {
        rawCommand = rawCommand.trim();
        if (rawCommand.isEmpty()) {
            return;
        }

        for (Command command : commands) {
            if (command.matches(rawCommand)) {
                runCommand(command, rawCommand);
                System.out.println();
                return;
            }
        }

        System.out.printf("Command '%s' not found.%n", commandFromRaw(rawCommand));
    }

    private void runCommand(Command command, String rawCommand) {
        String args = stripCommand(rawCommand);
        command.execute(loggedInUser, currentDirectory, args);
    }

    private Directory findRoot() {
        return (Directory) findEntryService.findEntry(loggedInUser, String.valueOf(Directory.SEPARATOR));
    }

    private Set<Command> createCommands() {
        Set<Command> commands = new HashSet<>();

        commands.add(new TouchCommand(this));
        commands.add(new LSCommand(this));
        commands.add(new CDCommand(this));
        commands.add(new ChModCommand(this));
        commands.add(new HelpCommand(this));
        commands.add(new MkDirCommand(this));
        commands.add(new CatCommand(this));
        commands.add(new EditCommand(this));
        commands.add(new CpCommand(this));
        commands.add(new MvCommand(this));

        return commands;
    }

    private static String stripCommand(String rawCommand) {
        int spaceIndex = rawCommand.indexOf(' ');
        if (spaceIndex > -1) {
            return rawCommand.substring(spaceIndex + 1);
        } else {
            return "";
        }
    }

    private static String commandFromRaw(String rawCommand) {
        rawCommand = rawCommand.trim();

        int spaceIndex = rawCommand.indexOf(' ');
        if (spaceIndex > -1) {
            return rawCommand.substring(0, spaceIndex);
        } else {
            return rawCommand;
        }
    }
}

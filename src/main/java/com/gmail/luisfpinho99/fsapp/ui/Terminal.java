package com.gmail.luisfpinho99.fsapp.ui;

import com.gmail.luisfpinho99.fsapp.domain.model.entry.Directory;
import com.gmail.luisfpinho99.fsapp.domain.service.FindEntryService;
import com.gmail.luisfpinho99.fsapp.domain.service.UpdateCurrentDirectoryService;
import com.gmail.luisfpinho99.fsapp.ui.commands.*;

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
        String rawCommand;

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
        Set<Command> commandSet = new HashSet<>();

        commandSet.add(new TouchCommand(this));
        commandSet.add(new LSCommand(this));
        commandSet.add(new CDCommand(this));
        commandSet.add(new ChModCommand(this));
        commandSet.add(new HelpCommand(this));
        commandSet.add(new MkDirCommand(this));
        commandSet.add(new CatCommand(this));
        commandSet.add(new EditCommand(this));
        commandSet.add(new CpCommand(this));
        commandSet.add(new MvCommand(this));

        return commandSet;
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

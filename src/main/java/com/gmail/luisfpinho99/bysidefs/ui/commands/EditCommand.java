package com.gmail.luisfpinho99.bysidefs.ui.commands;

import com.gmail.luisfpinho99.bysidefs.domain.model.entry.Directory;
import com.gmail.luisfpinho99.bysidefs.domain.model.user.User;
import com.gmail.luisfpinho99.bysidefs.domain.service.EditFileService;
import com.gmail.luisfpinho99.bysidefs.ui.Terminal;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EditCommand extends Command {

    public EditCommand(Terminal terminal) {
        super(terminal, "edit");
    }

    @Override
    protected void run(User user, Directory currentDirectory, List<String> args, Set<CommandParameter> parameters) {
        if (args.size() < 2) {
            throw new IllegalArgumentException("Usage of edit: edit Filename New content");
        }

        List<String> newContentList = IntStream.range(1, args.size()).mapToObj(args::get).collect(Collectors.toList());

        EditFileService editFileService = new EditFileService();
        editFileService.editFile(user, currentDirectory, args.get(0), joinArgs(newContentList));

        System.out.printf("The file '%s' was edited.%n", args.get(0));
    }
}

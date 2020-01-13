package com.gmail.luisfpinho99.fsapp.ui.commands;

import com.gmail.luisfpinho99.fsapp.domain.model.entry.Directory;
import com.gmail.luisfpinho99.fsapp.domain.model.user.User;
import com.gmail.luisfpinho99.fsapp.domain.service.ShowFileContentService;
import com.gmail.luisfpinho99.fsapp.ui.Terminal;

import java.util.List;
import java.util.Set;

public class CatCommand extends Command {

    public CatCommand(Terminal terminal) {
        super(terminal, "cat");
    }

    @Override
    protected void run(User user, Directory currentDirectory, List<String> args, Set<CommandParameter> parameters) {
        ShowFileContentService showFileContentService = new ShowFileContentService();

        System.out.printf("%n--- Begining of File ---%n");
        System.out.println(showFileContentService.showFileContent(user, currentDirectory, joinArgs(args)));
        System.out.printf("--- End of File ---%n");
    }
}

package com.gmail.luisfpinho99.fsapp.ui;

import com.gmail.luisfpinho99.fsapp.repository.PersistenceContext;
import com.gmail.luisfpinho99.fsapp.repository.jpa.RepositoryFactoryJPA;
import com.gmail.luisfpinho99.fsapp.repository.memory.RepositoryFactoryMem;

public class AskPersistenceUI extends UI {

    public boolean askPersistence() {
        System.out.print("Use persistence? (y/n/q)\n(mv command is buggy with persistence)\n> ");
        String answer;
        do {
            answer = scanner.nextLine();

            if (answer.equalsIgnoreCase("y")) {
                PersistenceContext.init(new RepositoryFactoryJPA());
                return true;
            } else if (answer.equalsIgnoreCase("n")) {
                PersistenceContext.init(new RepositoryFactoryMem());
                return true;
            }
        } while (!answer.equalsIgnoreCase("q"));

        return false;
    }
}

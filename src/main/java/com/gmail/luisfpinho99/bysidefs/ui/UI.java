package com.gmail.luisfpinho99.bysidefs.ui;

import com.gmail.luisfpinho99.bysidefs.domain.model.user.User;

import java.util.Scanner;

public abstract class UI {

    protected final Scanner scanner;
    protected User loggedInUser;

    public UI() {
        this.scanner = new Scanner(System.in);
    }

    public UI(User loggedInUser) {
        this.scanner = new Scanner(System.in);
        this.loggedInUser = loggedInUser;
    }

    protected void run(Runnable runnable) {
        try {
            runnable.run();
        } catch (IllegalArgumentException e) {
            System.out.printf("Error: %s%n", e.getMessage());
        } catch (Exception e) {
            System.out.printf("Error: %s%n", e.getMessage());
            e.printStackTrace();
        }
    }
}

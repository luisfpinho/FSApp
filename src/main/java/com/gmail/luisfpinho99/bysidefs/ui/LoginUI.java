package com.gmail.luisfpinho99.bysidefs.ui;

import com.gmail.luisfpinho99.bysidefs.authentication.LoginService;
import com.gmail.luisfpinho99.bysidefs.domain.model.user.User;
import com.gmail.luisfpinho99.bysidefs.ui.commands.HelpCommand;

public class LoginUI extends UI {

    public void login() {
        while (loggedInUser == null) {
            System.out.print("\nLOGIN\nUsername: ");
            String username = scanner.nextLine();

            System.out.print("Password: ");
            String password = scanner.nextLine();

            LoginService loginService = new LoginService();
            run(() -> {
                User user = loginService.login(username, password);
                if (user != null) {
                    this.loggedInUser = user;
                } else {
                    throw new IllegalArgumentException("Unknown user.");
                }
            });
        }

        System.out.printf("%nType '%s' for a list of commands or '%s' to logout.%n", HelpCommand.NAME, Terminal.EXIT_COMMAND);
    }
}

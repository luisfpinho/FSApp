package com.gmail.luisfpinho99.bysidefs;

import com.gmail.luisfpinho99.bysidefs.bootstrap.Bootstrapper;
import com.gmail.luisfpinho99.bysidefs.ui.Terminal;

public class Main {

    public static void main(String[] args) {
        Bootstrapper bootstrapper = new Bootstrapper();
        bootstrapper.bootstrap();

        Terminal terminal = new Terminal();
        terminal.start();
    }
}

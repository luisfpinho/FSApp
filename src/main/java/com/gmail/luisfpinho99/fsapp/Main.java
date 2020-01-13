package com.gmail.luisfpinho99.fsapp;

import com.gmail.luisfpinho99.fsapp.bootstrap.Bootstrapper;
import com.gmail.luisfpinho99.fsapp.ui.Terminal;

public class Main {

    public static void main(String[] args) {
        Bootstrapper bootstrapper = new Bootstrapper();
        bootstrapper.bootstrap();

        Terminal terminal = new Terminal();
        terminal.start();
    }
}

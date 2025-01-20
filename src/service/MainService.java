package service;

import model.User;
import service.swing.SwingService;
import service.terminal.TerminalService;
import ui.TerminalInterface;

import java.util.Scanner;

public class MainService {
    public static void start() {
        Scanner scanner = new Scanner(System.in);
        User loggedUser = null;
        boolean running = true;

        TerminalInterface terminal = new TerminalInterface();
        TerminalInterface.AskChoiceInterface();
        int choice = InputService.intInput(1,2, scanner);
        switch(choice) {
              case 1:
                  TerminalService.start();
                  break;
              case 2:
                  SwingService.start();
                  break;
        }

    }
}
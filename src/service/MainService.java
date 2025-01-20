package service;

import model.User;
import ui.TerminalInterface;

import java.util.Scanner;

public class MainService {
    public static void start() {
        Scanner scanner = new Scanner(System.in);
        User loggedUser = null;
        boolean running = true;

        TerminalInterface terminal = new TerminalInterface();
        TerminalInterface.AskChoiceInterface();
        int choice = InputService.intInput(1,2);
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
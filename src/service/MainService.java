package service;

import model.User;
import repository.Repository;
import service.swing.SwingService;
import service.terminal.TerminalService;
import ui.TerminalInterface;

import java.util.Scanner;

public class MainService {
    public static void start() {

        try {
            Repository.testConnection();
        }catch (Exception e){
            System.out.println("\nError : \n"+e.getMessage());
            System.exit(1);
        }

        Scanner scanner = new Scanner(System.in);
        User loggedUser = null;

        TerminalInterface.AskChoiceInterface();
        int choice = InputService.intInput(1,2, scanner);
        switch(choice) {
              case 1:
                  TerminalService.start();
                  break;
              case 2:
                  new SwingService();
                  break;
        }
        scanner.close();

    }
}
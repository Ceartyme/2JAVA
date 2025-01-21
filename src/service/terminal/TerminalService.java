package service.terminal;

import model.Response;
import model.User;
import repository.EmailRepository;
import repository.UserRepository;
import service.InputService;
import service.terminal.StoreService;
import ui.TerminalInterface;

import java.util.Scanner;

import service.terminal.UserService;

public class TerminalService {



    public static void start() {
        Scanner scanner = new Scanner(System.in);
        User loggedUser = null;
        boolean running = true;

        while (running) {
            TerminalInterface.showMainMenu();
            int choice = InputService.intInput(1, 3, scanner);


            switch (choice) {
                case 1:
                    UserService.loginController(scanner);
                    break;
                case 2:
                    UserService.registerController(scanner);
                    break;
                case 3:
                    System.out.println("Goodbye !");
                    running = false;
                    break;
            }
        }
    }



    public static void adminActions(Scanner scanner) {
        TerminalInterface.showAdminMenu();
        int choice = InputService.intInput(1, 3, scanner);

        switch (choice) {
            case 1:

        }

    }


    public static void userActions(Scanner scanner, User loggedUser) {
        boolean running = true;
        while(running) {
            TerminalInterface.showUserMenu();
            int choice = InputService.intInput(1, 5, scanner);

            switch (choice) {
                case 1:
                    StoreService.BrowseInventory(scanner);
                    break;
                case 2:
                    UserService.readInfoUsers(scanner);
                    break;
                case 3:
                    UserService.updateController(scanner, loggedUser);
                    break;
                case 4:
                    UserService.deleteController(scanner, loggedUser);
                case 5:
                    System.out.println("Logout !");
                    running = false;
                    break;
            }
        }

    }


}

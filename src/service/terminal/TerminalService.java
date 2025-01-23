package service.terminal;

import model.User;
import service.InputService;
import ui.TerminalInterface;

import java.util.Scanner;

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

        boolean running = true;

        while (running) {
            TerminalInterface.showAdminMenu();
            int choice = InputService.intInput(1, 4, scanner);

            switch (choice) {
                case 1:
                    ManageUsersActions(scanner);
                    break;
                case 2:
                    ManageStoresActions(scanner);
                    break;
                case 3:
                    break;
                case 4:
                    System.out.println("Goodbye !");
                    running = false;
                    break;
            }
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
                    UserService.updateControllerUser(scanner, loggedUser);
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


    public static void ManageUsersActions(Scanner scanner) {
        boolean running = true;
        while (running) {
            TerminalInterface.ManagerUsers();
            int choice = InputService.intInput(1, 3, scanner);
            switch (choice) {
                case 1:
                    UserService.updateControllerAdmin(scanner);
                    break;
                case 2:
                    UserService.deleteControllerAdmin(scanner);
                    break;
                case 3:
                    running = false;
                    break;
            }
        }
    }

    public static void ManageStoresActions(Scanner scanner) {
        boolean running = true;
        while (running) {
            TerminalInterface.ManagerStores();
            int choice = InputService.intInput(1, 6, scanner);
            switch (choice) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
            }
        }
    }


}

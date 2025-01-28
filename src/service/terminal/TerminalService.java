package service.terminal;

import model.User;
import repository.InventoryRepository;
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
            int choice = InputService.intInput(1, 5, scanner);
            switch (choice) {
                case 1:
                    UserService.updateControllerAdmin(scanner);
                    break;
                case 2:
                    EmailService.addWhitelistEmailController(scanner);
                    break;
                case 3:
                    EmailService.removeWhitelistEmailController(scanner);
                    break;
                case 4:
                    UserService.deleteControllerAdmin(scanner);
                    break;
                case 5:
                    running = false;
                    break;
            }
        }
    }

    public static void ManageStoresActions(Scanner scanner) {
        boolean running = true;
        while (running) {
            TerminalInterface.ManagerStores();
            int choice = InputService.intInput(1, 9, scanner);
            switch (choice) {
                case 1:
                    StoreService.createStoreController(scanner);
                    break;
                case 2:
                    StoreService.addEmployeesController(scanner);
                    break;
                case 3:
                    ItemService.createItemController(scanner);
                    break;
                case 4:
                    InventoryService.addOrUpdateItemsToStoreController(scanner);
                    break;
                case 5:
                    StoreService.displayWorkersController(scanner);
                    break;
                case 6:
                    InventoryService.deleteItemsFromStoreController(scanner);
                    break;
                case 7:
                    ItemService.deleteItemController(scanner);
                    break;
                case 8:
                    StoreService.deleteStoreController(scanner);
                    break;
                case 9:
                    running = false;
                    break;
            }
        }
    }

    public static void EmployeeActions(Scanner scanner, User loggedUser) {
        boolean running = true;
        while (running) {
            TerminalInterface.showEmployeeMenu();
            int choice = InputService.intInput(1, 6, scanner);

            switch (choice) {
                case 1:
                    TerminalService.manageMyStore(scanner, loggedUser);
                    break;
                case 2:
                    StoreService.BrowseInventory(scanner);
                    break;
                case 3:
                    UserService.readInfoUsers(scanner);
                    break;
                case 4:
                    UserService.updateControllerUser(scanner, loggedUser);
                    break;
                case 5:
                    UserService.deleteController(scanner, loggedUser);
                    break;
                case 6:
                    System.out.println("Logout !");
                    running = false;
                    break;

            }
        }
    }

    public static void manageMyStore(Scanner scanner, User loggedUser) {
        boolean running = true;
        while (running) {
            TerminalInterface.manageMyStoreMenu();
            int choice = InputService.intInput(1, 4, scanner);

            switch (choice) {
                case 1:
                    StoreService.displayWorkersEmployeesController(loggedUser.getIdUser(), scanner);
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    running = false;
                    break;

            }
        }
    }


}

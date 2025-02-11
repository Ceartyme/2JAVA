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

    public static void adminActions(Scanner scanner, User loggedUser) {
        boolean running = true;
        while (running) {
            TerminalInterface.showAdminMenu();
            int choice = InputService.intInput(1, 3, scanner);
            switch (choice) {
                case 1:
                    ManageUsersActions(scanner, loggedUser);
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
                    break;
                case 5:
                    System.out.println("Logout !");
                    running = false;
                    break;
            }
        }
    }

    public static void ManageUsersActions(Scanner scanner, User loggedUser) {
        boolean running = true;
        while (running) {
            TerminalInterface.ManagerUsers();
            int choice = InputService.intInput(1, 7, scanner);
            switch (choice) {
                case 1:
                    UserService.registerController(scanner);
                    break;
                case 2:
                    UserService.updateControllerAdmin(scanner, loggedUser);
                    break;
                case 3:
                    EmailService.addWhitelistEmailController(scanner);
                    break;
                case 4:
                    EmailService.removeWhitelistEmailController(scanner);
                    break;
                case 5:
                    UserService.deleteControllerAdmin(scanner, loggedUser);
                    break;
                case 6:
                    UserService.readInfoUsers(scanner);
                    break;
                case 7:
                    running = false;
                    break;
            }
        }
    }

    public static void ManageStoresActions(Scanner scanner) {
        boolean running = true;
        while (running) {
            TerminalInterface.ManagerStores();
            int choice = InputService.intInput(1, 12, scanner);
            switch (choice) {
                case 1:
                    scanner.nextLine();
                    StoreService.createStoreController(scanner);
                    break;
                case 2:
                    StoreService.updateStoreController(scanner);
                    break;
                case 3:
                    StoreService.addEmployeesController(scanner);
                    break;
                case 4:
                    ItemService.createItemController(scanner);
                    break;
                case 5:
                    ItemService.updateItemController(scanner);
                    break;
                case 6:
                    InventoryService.createItemsToStoreController(scanner);
                    break;
                case 7:
                    StoreService.displayWorkersController(scanner);
                    break;
                case 8:
                    InventoryService.deleteItemsFromStoreController(scanner);
                    break;
                case 9:
                    ItemService.deleteItemController(scanner);
                    break;
                case 10:
                    StoreService.removeEmployeesController(scanner);
                    break;
                case 11:
                    StoreService.deleteStoreController(scanner);
                    break;
                case 12:
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
                    InventoryService.increaseItemController(loggedUser.getIdUser(), scanner);
                    break;
                case 3:
                    InventoryService.decreaseItemController(loggedUser.getIdUser(), scanner);
                    break;
                case 4:
                    running = false;
                    break;
            }
        }
    }
}
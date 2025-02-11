package ui;

public class TerminalInterface {
    public static void AskChoiceInterface(){
        System.out.println("╔═══════════════════════════════════════════╗");
        System.out.println("║       Which interface do you want ?       ║");
        System.out.println("║                                           ║");
        System.out.println("║          1. Terminal                      ║");
        System.out.println("║          2. Swing Window                  ║");
        System.out.println("║                                           ║");
        System.out.println("╚═══════════════════════════════════════════╝");
    }

    public static void showMainMenu(){
        System.out.println("╔═══════════════════════════════════════════╗");
        System.out.println("║            Welcome to iStore              ║");
        System.out.println("║                                           ║");
        System.out.println("║          1. Login                         ║");
        System.out.println("║          2. Register                      ║");
        System.out.println("║          3. Exit                          ║");
        System.out.println("║                                           ║");
        System.out.println("╚═══════════════════════════════════════════╝");
    }

    public static void showAdminMenu() {
        System.out.println("╔═══════════════════════════════════════════╗");
        System.out.println("║              Admin Options                ║");
        System.out.println("║                                           ║");
        System.out.println("║          1. Manage Users                  ║");
        System.out.println("║          2. Manage Stores                 ║");
        System.out.println("║          3. Logout                        ║");
        System.out.println("║                                           ║");
        System.out.println("╚═══════════════════════════════════════════╝");
    }

    public static void ManagerUsers() {
        System.out.println("╔═══════════════════════════════════════════╗");
        System.out.println("║              Admin Options                ║");
        System.out.println("║              (Manage Users)               ║");
        System.out.println("║                                           ║");
        System.out.println("║          1. Create User                   ║");
        System.out.println("║          2. Update User                   ║");
        System.out.println("║          3. Add whitelisted email         ║");
        System.out.println("║          4. Remove whitelisted email      ║");
        System.out.println("║          5. Delete User                   ║");
        System.out.println("║          6. Display all Users             ║");
        System.out.println("║          7. Back                          ║");
        System.out.println("║                                           ║");
        System.out.println("║                                           ║");
        System.out.println("╚═══════════════════════════════════════════╝");
    }

    public static void ManagerStores() {
        System.out.println("╔═══════════════════════════════════════════╗");
        System.out.println("║              Admin Options                ║");
        System.out.println("║              (Manage Stores)              ║");
        System.out.println("║                                           ║");
        System.out.println("║          1. Create Store                  ║");
        System.out.println("║          2. Update Store                  ║");
        System.out.println("║          3. Add Employees to Store        ║");
        System.out.println("║          4. Create Items                  ║");
        System.out.println("║          5. Update Items                  ║");
        System.out.println("║          6. Create Items to Store         ║");
        System.out.println("║          7. Display Workers               ║");
        System.out.println("║          8. Delete Items to Store         ║");
        System.out.println("║          9. Delete Items                  ║");
        System.out.println("║         10. Remove Employees to Store     ║");
        System.out.println("║         11. Delete Store                  ║");
        System.out.println("║         12. Back                          ║");
        System.out.println("║                                           ║");
        System.out.println("║                                           ║");
        System.out.println("╚═══════════════════════════════════════════╝");
    }

    public static void showUserMenu() {
        System.out.println("╔═══════════════════════════════════════════╗");
        System.out.println("║               User Options                ║");
        System.out.println("║                                           ║");
        System.out.println("║          1. Browse Inventory              ║");
        System.out.println("║          2. Read information about        ║");
        System.out.println("║             users                         ║");
        System.out.println("║          3. Update Profile                ║");
        System.out.println("║          4. Delete Profile                ║");
        System.out.println("║          5. Logout                        ║");
        System.out.println("║                                           ║");
        System.out.println("╚═══════════════════════════════════════════╝");
    }

    public static void showEmployeeMenu() {
        System.out.println("╔═══════════════════════════════════════════╗");
        System.out.println("║             Employee Options              ║");
        System.out.println("║                                           ║");
        System.out.println("║          1. Manage my store               ║");
        System.out.println("║          2. Browse Inventory              ║");
        System.out.println("║          3. Read information about        ║");
        System.out.println("║             users                         ║");
        System.out.println("║          4. Update Profile                ║");
        System.out.println("║          5. Delete Profile                ║");
        System.out.println("║          6. Logout                        ║");
        System.out.println("║                                           ║");
        System.out.println("╚═══════════════════════════════════════════╝");
    }

    public static void manageMyStoreMenu() {
        System.out.println("╔═══════════════════════════════════════════╗");
        System.out.println("║             Employee Options              ║");
        System.out.println("║             (Manage my store)             ║");
        System.out.println("║                                           ║");
        System.out.println("║          1. Display Workers               ║");
        System.out.println("║          2. Increase items in stock       ║");
        System.out.println("║          3. Decrease items in stock       ║");
        System.out.println("║          4. Back                          ║");
        System.out.println("║                                           ║");
        System.out.println("╚═══════════════════════════════════════════╝");
    }
}
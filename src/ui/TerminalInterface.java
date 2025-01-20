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
        System.out.println("║          Welcome to iStore Ltd!           ║");
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

    public static void showUserMenu() {
        System.out.println("╔═══════════════════════════════════════════╗");
        System.out.println("║               User Options                ║");
        System.out.println("║                                           ║");
        System.out.println("║          1. Browse Inventory              ║");
        System.out.println("║          2. Update Profile                ║");
        System.out.println("║          3. Logout                        ║");
        System.out.println("║                                           ║");
        System.out.println("╚═══════════════════════════════════════════╝");
    }
}

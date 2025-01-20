package service.terminal;

import model.Response;
import model.User;
import repository.EmailRepository;
import repository.UserRepository;

import java.util.Scanner;

public class UserService {
    public static void loginController(Scanner scanner) {
        scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        Response<User> response = UserRepository.login(email, password);

        if (response.getMessage().equals("Success")){
            User userLogged = response.getValue();
            if (userLogged != null) {
                System.out.println("Logged in. Welcome " + userLogged.getUsername());

                switch (userLogged.getRole()) {
                    case ADMIN:
                        TerminalService.adminActions(scanner);
                        break;
                    case EMPLOYEE:
                        TerminalService.userActions(scanner);
                        break;
                    case USER:
                        TerminalService.userActions(scanner);

                        break;

                }








            }else{
                System.out.println("Login failed.");
            }
        }else{
            System.out.println("Error Message :" + response.getMessage());
        }



    }

    public static void registerController(Scanner scanner) {
        scanner.nextLine();
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();


        Response<Boolean> whitelistResponse = EmailRepository.isEmailWhitelisted(email);

        if(!whitelistResponse.getMessage().equals("Success") || !whitelistResponse.getValue()){
            if (!whitelistResponse.getMessage().equals("Success")){
                System.out.println("Error Message :" + whitelistResponse.getMessage());
            }else{
                System.out.println("Registration failed. Email is not whitelisted.");
            }
            return;
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        Response<User> response = UserRepository.createUser(username, email, password, 3);

        if (response.getMessage().equals("Success")){
            User newUser = response.getValue();
            if (newUser != null){
                System.out.println("Registration successful. Welcome, " + newUser.getUsername() + "!");
            }else{
                System.out.println("Error: User was not created, but operation was marked successful.");
            }
        }else{
            System.out.println("Error Message :" + response.getMessage());
        }

    }


}

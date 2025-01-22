package service.terminal;

import model.Response;
import model.User;
import repository.EmailRepository;
import repository.UserRepository;
import service.InputService;

import java.util.ArrayList;
import java.util.Scanner;

public class UserService {
    public static void loginController(Scanner scanner) {
        scanner.nextLine();

        String email = InputService.emailInput(scanner);
        scanner.nextLine();

        System.out.println("Enter password: ");
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
                        TerminalService.userActions(scanner, userLogged);
                        break;
                    case USER:
                        TerminalService.userActions(scanner, userLogged);

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
        String username;
        String email;

        while(true){
            System.out.print("Enter Username: ");
            username = scanner.nextLine();

            Response<User> response = UserRepository.getUserByUsername(username);
            if (response.getMessage().equals("Success") && response.getValue() != null) {
                System.out.println("This username is already taken. Please choose another.");
            }else{
                break;
            }
        }

        while(true){

            email = InputService.emailInput(scanner);
            Response<User> response = UserRepository.getUserByEmail(email);

            if (response.getMessage().equals("Success") && response.getValue() != null) {
                System.out.println("This email is already taken. Please choose another.");
            }else{
                Response<Boolean> whitelistResponse = EmailRepository.isEmailWhitelisted(email);

                if(!whitelistResponse.getMessage().equals("Success") || !whitelistResponse.getValue()){
                    if (!whitelistResponse.getMessage().equals("Success")){
                        System.out.println("Error Message :" + whitelistResponse.getMessage());
                    }else{
                        System.out.println("Registration failed. Email is not whitelisted.");
                        return;
                    }

                }else{
                    break;
                }
            }
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

    public static void updateController(Scanner scanner, User loggedUser) {
        scanner.nextLine();
        System.out.println("You will edit your profil !");
        System.out.println("Current Username: " + loggedUser.getUsername());
        System.out.println("Current Email: " + loggedUser.getEmail());

        System.out.println("Enter new Email (or press enter to keep current) :");
        String newEmail = scanner.nextLine().trim();
        if (newEmail.isEmpty()){
            newEmail = loggedUser.getEmail();
        }else{
            loggedUser.setEmail(newEmail);
        }

        System.out.println("Enter new Username (or press enter to keep current) :");
        String newUsername = scanner.nextLine().trim();
        if (newUsername.isEmpty()){
            newUsername = loggedUser.getUsername();
        }else{
            loggedUser.setUsername(newUsername);
        }

        System.out.println("Enter new Password (or press enter to keep current) :");
        String newPassword = scanner.nextLine().trim();
        if (newPassword.isEmpty()){
            newPassword = loggedUser.getPassword();
        }else{
            loggedUser.setPassword(newPassword);
        }


        Response<User> updateResponse = UserRepository.updateUser(loggedUser);

        if (updateResponse.getMessage().equals("Success")){
            System.out.println("Profil updated successfully.");
        }else{
            System.out.println("Error Message :" + updateResponse.getMessage());
        }
    }

    public static void readInfoUsers(Scanner scanner) {
        Response<ArrayList<User>> response = UserRepository.getAllUsers();

        if (!response.getMessage().equals("Success")){
            System.out.println("Error : " + response.getMessage());
        }

        ArrayList<User> users = response.getValue();

        System.out.println("Users information:");
        System.out.println("ID | Username | Email | Role");
        System.out.println("-----------------------------");

        for (User user : users) {
            System.out.printf("%d | %s | %s | %s%n",
                    user.getIdUser(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getRole()
            );
        }
    }

    public static void deleteController(Scanner scanner, User loggedUser) {
        String response = UserRepository.deleteUser(loggedUser.getIdUser());

        if (response.equals("User Deleted Successfully")){
            System.out.println("The user has been successfully deleted.");
        }else{
            System.out.println("Error Message :" + response);
        }
    }


}

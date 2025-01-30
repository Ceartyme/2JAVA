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
                        TerminalService.adminActions(scanner, userLogged);
                        break;
                    case EMPLOYEE:
                        TerminalService.EmployeeActions(scanner, userLogged);
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
            Response<Boolean> response = UserRepository.isUsernameExisting(username);
            if (response.getMessage().equals("Success") && response.getValue()) {
                System.out.println("This username is already taken. Please choose another.");
            } else if (!response.getMessage().equals("Success")) {
                System.out.println("Error Message :" + response.getMessage());
            }
            else {
                break;
            }
        }

        while(true){
            email = InputService.emailInput(scanner);
            Response<Boolean> response = UserRepository.isEmailExisting(email);
            if (response.getMessage().equals("Success") && response.getValue()) {
                System.out.println("This email is already taken. Please choose another.");
            }else if(!response.getMessage().equals("Success")){
                System.out.println("Error Message :" + response.getMessage());
            } else{
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
        scanner.nextLine();
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

    public static void updateControllerUser(Scanner scanner, User loggedUser) {
        scanner.nextLine();
        System.out.println("You will edit your profil !");
        System.out.println("Current Username: " + loggedUser.getUsername());
        System.out.println("Current Email: " + loggedUser.getEmail());

        while (true){
            System.out.println("Enter new Username (or press enter to keep current) :");
            String newUsername = scanner.nextLine().trim();
            if (newUsername.isEmpty()){
                newUsername = loggedUser.getUsername();
                break;
            }else{
                Response<Boolean> usernameCheckResponse = UserRepository.isUsernameExisting(newUsername);
                if (usernameCheckResponse.getMessage().equals("Success") && usernameCheckResponse.getValue()) {
                    System.out.println("This username is already taken. Please choose another.");
                } else if (!usernameCheckResponse.getMessage().equals("Success")) {
                    System.out.println("Error Message: " + usernameCheckResponse.getMessage());
                }else {
                    loggedUser.setUsername(newUsername);
                    break;
                }
            }
        }

        while (true){
            System.out.println("Enter new Email ( or press enter to keep current) :");
            String newEmail = scanner.nextLine().trim();
            if (newEmail.isEmpty()){
                newEmail = loggedUser.getEmail();
                break;
            }
            if (!InputService.isEmailValid(newEmail)){
                System.out.println("Email is not valid. Please try again.");
            } else{
                Response<Boolean> response = UserRepository.isEmailExisting(newEmail);
                if (response.getMessage().equals("Success") && response.getValue()) {
                    System.out.println("This email is already taken. Please choose another.");
                }else if(!response.getMessage().equals("Success")){
                    System.out.println("Error Message :" + response.getMessage());
                } else {
                    Response<Boolean> whitelistResponse = EmailRepository.isEmailWhitelisted(newEmail);
                    if (!whitelistResponse.getMessage().equals("Success") || !whitelistResponse.getValue()) {
                        if (!whitelistResponse.getMessage().equals("Success")) {
                            System.out.println("Error Message :" + whitelistResponse.getMessage());
                        } else {
                            System.out.println("Registration failed. Email is not whitelisted.");
                            return;
                        }
                    } else {
                        break;
                    }
                }
            }
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

    public static void updateControllerAdmin(Scanner scanner) {

        System.out.println("Fetching all users...");
        readInfoUsers(scanner);
        Response<ArrayList<Integer>> userIdResponse = UserRepository.getIdUser();
        int idUser = InputService.idInput(scanner, userIdResponse);

        if (idUser == 0){
            return;
        }

        Response<User> response = UserRepository.getUserById(idUser);
        if (!response.getMessage().equals("Success")){
            System.out.println("Error Message :" + response.getMessage());
        }

        User userToUpdate = response.getValue();
        System.out.println("Current user details:");
        System.out.printf("ID: %d | Username: %s | Email: %s | Role: %s%n",
                userToUpdate.getIdUser(),
                userToUpdate.getUsername(),
                userToUpdate.getEmail(),
                userToUpdate.getRole()
        );

        while (true){
            scanner.nextLine();
            System.out.println("Enter new Username (or press enter to keep current) :");
            String newUsername = scanner.nextLine().trim();
            if (newUsername.isEmpty()){
                newUsername = userToUpdate.getUsername();
                break;
            }else{
                Response<Boolean> usernameCheckResponse = UserRepository.isUsernameExisting(newUsername);
                if (usernameCheckResponse.getMessage().equals("Success") && usernameCheckResponse.getValue()) {
                    System.out.println("This username is already taken. Please choose another.");
                } else if (!usernameCheckResponse.getMessage().equals("Success")) {
                    System.out.println("Error Message: " + usernameCheckResponse.getMessage());
                }else {
                    userToUpdate.setUsername(newUsername);
                    break;
                }
            }
        }

        while (true){
            System.out.println("Enter new Email ( or press enter to keep current) :");
            String newEmail = scanner.nextLine().trim();
            if (newEmail.isEmpty()){
                newEmail = userToUpdate.getEmail();
                break;
            }

            if (!InputService.isEmailValid(newEmail)){
                System.out.println("Email is not valid. Please try again.");
            } else {
                Response<Boolean> CheckEmailResponse = UserRepository.isEmailExisting(newEmail);
                if (response.getMessage().equals("Success") && CheckEmailResponse.getValue()) {
                    System.out.println("This email is already taken. Please choose another.");
                } else if (!CheckEmailResponse.getMessage().equals("Success")) {
                    System.out.println("Error Message :" + CheckEmailResponse.getMessage());
                } else {
                    Response<Boolean> whitelistResponse = EmailRepository.isEmailWhitelisted(newEmail);

                    if (!whitelistResponse.getMessage().equals("Success") || !whitelistResponse.getValue()) {
                        if (!whitelistResponse.getMessage().equals("Success")) {
                            System.out.println("Error Message :" + whitelistResponse.getMessage());
                        } else {
                            System.out.println("Registration failed. Email is not whitelisted.");
                            return;
                        }

                    } else {
                        break;
                    }
                }
            }
        }

        System.out.println("Enter new password (or press Enter to keep current):");
        String newPassword = scanner.nextLine().trim();
        if (!newPassword.isEmpty()) {
            userToUpdate.setPassword(newPassword);
        }

        System.out.println("Enter new role (1 = ADMIN, 2 = EMPLOYEE, 3 = USER) (or press Enter to keep current):");
        String newRole = scanner.nextLine().trim();
        if (!newRole.isEmpty()) {
            try {
                int roleId = Integer.parseInt(newRole);
                userToUpdate.setRole(roleId);
            } catch (NumberFormatException e) {
                System.out.println("Invalid role ID. No changes made to the role.");
            }
        }

        Response<User> updateResponse = UserRepository.updateUser(userToUpdate);
        if (updateResponse.getMessage().equals("Success")) {
            System.out.println("User updated successfully.");
        } else {
            System.out.println("Error updating user: " + updateResponse.getMessage());
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
        scanner.nextLine();
        System.out.println("Are you sure you want to delete the user? (yes/no)");
        String answer = scanner.nextLine();
        if (answer.equals("no")){
            System.out.println("Operation cancelled.");
            return;
        }

        String response = UserRepository.deleteUser(loggedUser.getIdUser());
        if (response.equals("User Deleted Successfully")){
            System.out.println("The user has been successfully deleted.");
        }else{
            System.out.println("Error Message :" + response);
        }
    }

    public static void deleteControllerAdmin(Scanner scanner, User loggedUser) {
        System.out.println("Fetching all users...");
        readInfoUsers(scanner);
        Response<ArrayList<Integer>> userIdResponse = UserRepository.getIdUser();
        int idUser = InputService.idInput(scanner, userIdResponse);
        Response<User> response = UserRepository.getUserById(idUser);
        if(!response.getMessage().equals("Success")){
            System.out.println("Error Message :" + response.getMessage());
            return;
        }

        User userToDelete = response.getValue();
        if(userToDelete == null){
            System.out.println("User does not exist. Please choose another.");
        }

        scanner.nextLine();
        System.out.println("Are you sure you want to delete the user? (yes/no)");
        String answer = scanner.nextLine();
        if (answer.equals("no")){
            System.out.println("Operation cancelled.");
            return;
        }

        String responseDelete = UserRepository.deleteUser(idUser);
        if (responseDelete.equals("User Deleted Successfully")) {
            System.out.println("The user has been successfully deleted.");
        } else {
            System.out.println("Error Message: " + responseDelete);
        }

        if (loggedUser.getIdUser() == userToDelete.getIdUser()) {
            loggedUser = null;
            System.out.println("Logout !");
            TerminalService.start();
        }
    }
}
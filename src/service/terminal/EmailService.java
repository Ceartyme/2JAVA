package service.terminal;

import model.Response;
import model.User;
import repository.EmailRepository;
import repository.UserRepository;
import service.InputService;

import java.util.ArrayList;
import java.util.Scanner;

public class EmailService {
    public static void addWhitelistEmailController(Scanner scanner) {
        scanner.nextLine();
        String email = InputService.emailInput(scanner);

        if (email == null || email.isEmpty()) {
            System.out.println("Email cannot be empty. Operation cancelled.");
            return;
        }

        Response<ArrayList<String>> allEmailsResponse = EmailRepository.getAllEmails();

        if (!allEmailsResponse.getMessage().equals("Success")) {
            System.out.println("Error fetching emails: " + allEmailsResponse.getMessage());
            return;
        }

        ArrayList<String> emails = allEmailsResponse.getValue();

        if (emails.contains(email)) {
            System.out.println("This email is already whitelisted. Please enter a different email.");
            addWhitelistEmailController(scanner);
            return;
        }

        String result = EmailRepository.whitelistEmail(email);
        if (result.equals("Email whitelisted Successfully")) {
            System.out.println("The email address has been successfully whitelisted.");
        } else {
            System.out.println("Error while whitelisting the email: " + result);
        }
    }

    public static void removeWhitelistEmailController(Scanner scanner) {
        scanner.nextLine();
        System.out.println("Fetching all whitelisted emails...");

        Response<ArrayList<String>> allEmailsResponse = EmailRepository.getAllEmails();
        if (!allEmailsResponse.getMessage().equals("Success")) {
            System.out.println("Error fetching emails: " + allEmailsResponse.getMessage());
            return;
        }

        ArrayList<String> emails = allEmailsResponse.getValue();
        if (emails.isEmpty()) {
            System.out.println("No whitelisted emails available.");
            return;
        }

        System.out.println("Whitelisted Emails:");
        for (int i = 0; i < emails.size(); i++) {
            System.out.printf("ID: %d | Email: %s%n", i + 1, emails.get(i));
        }

        System.out.println("Enter the ID of the email you want to remove (0 to cancel):");
        int emailId = InputService.intInput(0, emails.size(), scanner);

        if (emailId == 0){
            System.out.println("Operation cancelled.");
            return;
        }

        String selectedEmail = emails.get(emailId - 1);
        String result = EmailRepository.removeEmail(selectedEmail);

        if (result.equals("Mail address removed Successfully")) {
            System.out.println("The email address has been successfully removed from the whitelist.");

            Response<User> userResponse = UserRepository.getUserByEmail(selectedEmail);

            if (userResponse.getMessage().equals("Success") && userResponse.getValue() != null) {
                User user = userResponse.getValue();
                System.out.printf("User associated with this email: ID: %d | Username: %s | Role: %s%n",
                        user.getIdUser(), user.getUsername(), user.getRole());

                scanner.nextLine();
                System.out.println("Do you also want to delete this user? (yes/no)");
                String choice = scanner.nextLine().trim().toLowerCase();
                if (choice.equals("yes")) {
                    String deleteUserResult = UserRepository.deleteUser(user.getIdUser());
                    if (deleteUserResult.equals("User Deleted Successfully")) {
                        System.out.println("The user has been successfully deleted.");
                    } else {
                        System.out.println("Error deleting user: " + deleteUserResult);
                    }
                } else {
                    System.out.println("User was not deleted.");
                }

            }else{
                    System.out.println("No user found with this email.");
            }



        } else {
            System.out.println("Error while removing the email: " + result);
        }
    }
}
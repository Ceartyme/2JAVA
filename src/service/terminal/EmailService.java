package service.terminal;

import model.Response;
import repository.EmailRepository;
import service.InputService;

import java.util.ArrayList;
import java.util.Scanner;

public class EmailService {
    public static void addWhitelistEmailController(Scanner scanner) {
        scanner.nextLine();
        System.out.print("Enter email address: ");
        String email = scanner.nextLine();

        if (email == null || email.isEmpty()) {
            System.out.println("Email cannot be empty. Operation cancelled.");
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

        System.out.println("Enter the ID of the email you want to remove:");
        int emailId = InputService.intInput(1, emails.size(), scanner);

        String selectedEmail = emails.get(emailId - 1);

        String result = EmailRepository.removeEmail(selectedEmail);

        if (result.equals("Mail address removed Successfully")) {
            System.out.println("The email address has been successfully removed from the whitelist.");
        } else {
            System.out.println("Error while removing the email: " + result);
        }
    }

}

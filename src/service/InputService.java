package service;

import model.Response;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class InputService{
    public static int intInput(int minimum, int maximum, Scanner input){
        try {
            System.out.println("Enter an number between "+minimum+" and "+maximum+" : ");
            int response = input.nextInt();
            if(response>maximum || response<minimum){
                return intInput(minimum, maximum, input);
            }
            return response;
        }catch (InputMismatchException e){
            input.nextLine();
            return intInput(minimum, maximum, input);
        }
    }

    public static double doubleInput(double minimum, Scanner input) {
        try {
            System.out.println("Enter a number greater than " + minimum + ": ");
            double response = input.nextDouble();

            if (response == 0) {
                return 0;
            }

            if (response < minimum) {
                System.out.println("The number is less than the minimum allowed. Please try again.");
                return doubleInput(minimum, input);
            }
            return response;
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            input.nextLine();
            return doubleInput(minimum, input);
        }
    }

    public static String emailInput(Scanner input){
        System.out.println("Enter email address : ");
        String email=input.next();
        if (!isEmailValid(email)){
            input.nextLine();
            System.out.println("Invalid email address");
            return emailInput(input);
        }
        return email;
    }

    public static boolean isEmailValid(String email){
        String emailRegex = "^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,3}$";
        return Pattern.compile(emailRegex).matcher(email).matches();
    }

    public static int idInput(Scanner scanner, Response<ArrayList<Integer>> response) {
        if (!response.getMessage().equals("Success") || response.getValue() == null || response.getValue().isEmpty()) {
            System.out.println("Error: No valid IDs available.");
            return -1;
        }
        ArrayList<Integer> validIds = response.getValue();
        int input;
        while (true) {
            System.out.print("Enter a valid ID (or 0 to cancel): ");
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a numeric ID.");
                scanner.next();
                continue;
            }
            input = scanner.nextInt();
            if (input == 0) {
                return 0;
            }
            if (validIds.contains(input)) {
                return input;
            } else {
                System.out.println("Invalid ID. Please enter a valid ID");
            }
        }
    }
}

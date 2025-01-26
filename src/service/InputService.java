package service;

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
}

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

    public static String emailInput(Scanner input){
        System.out.println("Enter a valid email address : ");
        String email=input.next();
        if (!isEmailValid(email)){
            emailInput(input);
        }
        return email;
    }

    public static boolean isEmailValid(String email){
        String emailRegex = "^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,3}$";
        return Pattern.compile(emailRegex).matcher(email).matches();
    }
}

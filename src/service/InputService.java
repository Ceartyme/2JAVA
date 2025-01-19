package service;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputService{
    public static int intInput(int minimum, int maximum){
        Scanner input = new Scanner(System.in);
        try {
            System.out.println("Enter an number between "+minimum+" and "+maximum+" : ");
            int response = input.nextInt();
            if(response>maximum || response<minimum){
                return intInput(minimum, maximum);
            }
            return response;
        }catch (InputMismatchException e){
            return intInput(minimum, maximum);
        }finally {
            input.close();
        }
    }
}

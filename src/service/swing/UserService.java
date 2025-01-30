package service.swing;

import model.Response;
import model.User;
import repository.UserRepository;
import service.InputService;

import javax.swing.*;
import java.util.Objects;

public class UserService{
    public static User login(String email,String password) throws Exception {
        if(email.isEmpty()||password.isEmpty()){
            throw new Exception("Every field must be filled");
        }
        if(!InputService.isEmailValid(email)){
            throw new Exception("You must enter a valid Email");
        }

        Response<User> response = UserRepository.login(email, password);
        if(Objects.equals(response.getMessage(), "Incorrect Email or Password")){
            throw new Exception("Incorrect Email or Password");
        }else if (Objects.equals(response.getMessage(),"Success")){
            return response.getValue();
        }else {
            throw new Exception("Problem connecting to the database");
        }
    }
}

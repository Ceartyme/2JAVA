package service.swing;

import model.Response;
import model.User;
import repository.EmailRepository;
import repository.UserRepository;
import service.InputService;

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

    public static User register(String username, String email, String password) throws Exception{
        if(email.isEmpty()||password.isEmpty()||username.isEmpty()){
            throw new Exception("Every field must be filled");
        }
        if(!InputService.isEmailValid(email)){
            throw new Exception("You must enter a valid Email");
        }
        Response<Boolean> response = EmailRepository.isEmailWhitelisted(email);
        checkResponse(response);
        if(!response.getValue()){
            throw new Exception("This Email is not whitelisted in the database");
        }
        response = UserRepository.isEmailExisting(email);
        checkResponse(response);
        if(response.getValue()){
            throw new Exception("There is already an account linked to that email");
        }
        response = UserRepository.isUsernameExisting(username);
        checkResponse(response);
        if(response.getValue()){
            throw new Exception("You cannot use that username, it is already used");
        }
        Response<User> responseUser = UserRepository.createUser(username,email,password,3);
        if (Objects.equals(responseUser.getMessage(),"Success")){
            return responseUser.getValue();
        }
        else {
            throw new Exception("Problem connecting to the database");
        }
    }

    private static void checkResponse(Response response) throws Exception{
        if(!response.getMessage().equals("Success")){
            throw new Exception(response.getMessage());
        }

    }
}
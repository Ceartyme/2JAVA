package service.swing;

import model.*;
import repository.EmailRepository;
import repository.UserRepository;
import repository.WorkingRepository;
import service.InputService;

import java.util.ArrayList;
import java.util.Objects;


public class UserService{
    protected static User login(String email,String password) throws Exception {
        if(email.isEmpty()||password.isEmpty()||Objects.equals(email,"example@example.com")){
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

    protected static User createUser(String username, String email, String password,Role role) throws Exception{
        if(email.isEmpty()||password.isEmpty()||username.isEmpty()||Objects.equals(email,"example@example.com")){
            throw new Exception("Every field must be filled");
        }
        if(!InputService.isEmailValid(email)){
            throw new Exception("You must enter a valid Email");
        }
        Response<Boolean> response = EmailRepository.isEmailWhitelisted(email);
        GeneralService.checkResponse(response);
        if(!response.getValue()){
            throw new Exception("This Email is not whitelisted in the database");
        }
        response = UserRepository.isEmailExisting(email);
        GeneralService.checkResponse(response);
        if(response.getValue()){
            throw new Exception("There is already an account linked to that email");
        }
        response = UserRepository.isUsernameExisting(username);
        GeneralService.checkResponse(response);
        if(response.getValue()){
            throw new Exception("You cannot use that username, it is already used");
        }
        Response<User> responseUser = UserRepository.createUser(username,email,password,role.ordinal()+1);
        if (Objects.equals(responseUser.getMessage(),"Success")){
            return responseUser.getValue();
        }
        else {
            throw new Exception("Problem connecting to the database");
        }
    }

    protected static void update(String username, String email, String password, User user) throws Exception{
        if(!password.isEmpty()){
            user.setPassword(password);
        }
        if(username.isEmpty()||email.isEmpty()){
            throw new Exception("Email and Username can't be empty");
        }
        Response<Boolean> response = UserRepository.isUsernameExisting(username);
        GeneralService.checkResponse(response);
        if(!Objects.equals(username,user.getUsername())) {
            if (response.getValue()) {
                throw new Exception("You cannot use that username, it is already used");
            } else {
                user.setUsername(username);
            }
        }
        response = UserRepository.isEmailExisting(email);
        GeneralService.checkResponse(response);
        if (!Objects.equals(email,user.getEmail())) {
            if (response.getValue()) {
                throw new Exception("There is already an account linked to that email");
            } else {
                user.setEmail(email);
            }
        }
        Response<User> responseUser = UserRepository.updateUser(user);
        GeneralService.checkResponse(responseUser);
    }

    protected static void adminUpdate(String username, String email, String password, Role role, User user) throws Exception{
        if(!password.isEmpty()){
            user.setPassword(password);
        }
        if(username.isEmpty()||email.isEmpty()){
            throw new Exception("Email and Username can't be empty");
        }
        if(role!=user.getRole()){
            user.setRole(role);
        }
        Response<Boolean> response = UserRepository.isUsernameExisting(username);
        GeneralService.checkResponse(response);
        if(!Objects.equals(username,user.getUsername())) {
            if (response.getValue()) {
                throw new Exception("You cannot use that username, it is already used");
            } else {
                user.setUsername(username);
            }
        }
        response = UserRepository.isEmailExisting(email);
        GeneralService.checkResponse(response);
        if (!Objects.equals(email,user.getEmail())) {
            if (response.getValue()) {
                throw new Exception("There is already an account linked to that email");
            } else {
                user.setEmail(email);
            }
        }
        Response<User> responseUser = UserRepository.updateUser(user);
        GeneralService.checkResponse(responseUser);
    }

    protected static void deleteUser(User user){
        UserRepository.deleteUser(user.getIdUser());
    }

    protected static ArrayList<User> getAllUsers() throws Exception{
        Response<ArrayList<User>> allUsers = UserRepository.getAllUsers();
        if(Objects.equals(allUsers.getMessage(), "Error not any user in database")){
            return new ArrayList<>();
        }
        GeneralService.checkResponse(allUsers);
        return allUsers.getValue();
    }

    protected static void removeStores(User user){
        WorkingRepository.removeStoresFromUser(user.getIdUser());
    }

    protected static int getAmountStore(User user){
        Response<ArrayList<Store>> storeResponse =WorkingRepository.getStoresFromEmployee(user.getIdUser());
        if(storeResponse.getValue()==null){
            return 0;
        }
        return storeResponse.getValue().size();
    }

    public static User getUserByName(String username) {
        return UserRepository.getUserByUsername(username).getValue();
    }
}
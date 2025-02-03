package service.swing;

import model.Item;
import model.Response;
import model.User;
import repository.EmailRepository;
import repository.ItemRepository;
import repository.UserRepository;
import service.InputService;

import java.util.ArrayList;
import java.util.Objects;

public class EmailService {
    protected static void changeEmail(String oldEmail, String newEmail){
        EmailRepository.removeEmail(oldEmail);
        EmailRepository.whitelistEmail(newEmail);
    }

    protected static boolean isWhitelisted(String email)throws Exception{
        if(!InputService.isEmailValid(email)){
            throw new Exception("You must enter a valid Email");
        }
        Response<Boolean> response = EmailRepository.isEmailWhitelisted(email);
        GeneralService.checkResponse(response);
        return response.getValue();
    }

    protected static void whitelistEmail(String email)throws Exception{
        if(!InputService.isEmailValid(email)){
            throw new Exception("You must enter a valid Email");
        }
        EmailRepository.whitelistEmail(email);
    }

    protected static ArrayList<String> getAllEmail() throws Exception{
        Response<ArrayList<String>> allEmail = EmailRepository.getAllEmails();
        if(Objects.equals(allEmail.getMessage(), "There are no whitelisted email")){
            return new ArrayList<>();
        }
        GeneralService.checkResponse(allEmail);
        return allEmail.getValue();
    }

    protected static void deleteEmail(String email) throws Exception{
        String response = EmailRepository.removeEmail(email);
        if(!Objects.equals(response,"Mail address removed Successfully")){
            throw new Exception(response);
        }
    }
}

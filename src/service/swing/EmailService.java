package service.swing;

import model.Response;
import repository.EmailRepository;
import service.InputService;

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
}

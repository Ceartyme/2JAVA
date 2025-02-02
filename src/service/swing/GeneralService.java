package service.swing;

import model.Response;

public class GeneralService {
    protected static void checkResponse(Response response) throws Exception{
        if(!response.getMessage().equals("Success")){
            throw new Exception(response.getMessage());
        }
    }
}

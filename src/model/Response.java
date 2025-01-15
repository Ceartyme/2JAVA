package model;

import java.util.Objects;

public class Response<T> {
    private T value;
    private final String message;

    public Response(T value){
        this.value=value;
        this.message="Success";
    }
    public Response(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    public T getValue(){
        if ((Objects.equals(message, "Success"))) {
            return value;
        }
        return null;
    }
}

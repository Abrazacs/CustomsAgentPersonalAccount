package ru.ssemenov.exceptions;

public class DeleteException extends RuntimeException {

    public DeleteException(String message){
        super(message);
    }

}
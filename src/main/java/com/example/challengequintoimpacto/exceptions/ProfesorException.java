package com.example.challengequintoimpacto.exceptions;

public class ProfesorException extends BaseException{
    public static final String NOT_FOUND="No existe un profesor con ese id";
    public ProfesorException(String errorMessage){
        super(errorMessage);
    }
}

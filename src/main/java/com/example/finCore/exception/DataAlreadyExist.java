package com.example.finCore.exception;

public class DataAlreadyExist extends RuntimeException{
    public DataAlreadyExist(String message) {
        super(message);
    }
}
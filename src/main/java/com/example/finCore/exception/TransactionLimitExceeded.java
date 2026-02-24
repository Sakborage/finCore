package com.example.finCore.exception;

public class TransactionLimitExceeded extends RuntimeException{

    public TransactionLimitExceeded(String s){
        super(s);
    }
}

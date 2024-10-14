package com.nhnacademy.account.exception;

public class AccountNotFoundException extends IllegalStateException{
    public AccountNotFoundException(String message) {
        super(message);
    }
}

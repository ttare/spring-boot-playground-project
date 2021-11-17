package com.example.imagesharingapi.exceptions;

public class EmailExistsException extends RuntimeException {

    public EmailExistsException() {
        super("There is an account with that email address: ");
    }
}

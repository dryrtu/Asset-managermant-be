package com.example.asset_management_idnes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AccountNotActivedException extends RuntimeException {

    public AccountNotActivedException(String message) {
        super(message);
    }


}

package com.joshuamonk.phoneapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PhoneNumberInUseException extends RuntimeException {
    public PhoneNumberInUseException(String message) {
        super(message);
    }
}

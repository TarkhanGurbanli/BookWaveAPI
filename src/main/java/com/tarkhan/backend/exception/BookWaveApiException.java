package com.tarkhan.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BookWaveApiException extends RuntimeException {
    public BookWaveApiException(String message){
        super(message);
    }
}

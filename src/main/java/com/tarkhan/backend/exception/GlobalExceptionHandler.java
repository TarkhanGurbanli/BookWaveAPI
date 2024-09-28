package com.tarkhan.backend.exception;

import com.tarkhan.backend.model.ErrorResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // BookWaveApiException handling
    @ExceptionHandler(BookWaveApiException.class)
    public ResponseEntity<ErrorResponseModel> handleBookWaveApiException(
            BookWaveApiException ex,
            WebRequest request
    ) {
        logger.error("BookWaveApiException occurred: {}", ex.getMessage());
        ErrorResponseModel error = new ErrorResponseModel(
                request.getDescription(false),
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // ResourceNotFoundException handling
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseModel> handleResourceNotFoundException(
            ResourceNotFoundException ex,
            WebRequest request
    ) {
        logger.error("ResourceNotFoundException occurred: {}", ex.getMessage());
        ErrorResponseModel error = new ErrorResponseModel(
                request.getDescription(false),
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // RuntimeException handling
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseModel> handleRuntimeException(
            RuntimeException ex,
            WebRequest request
    ) {
        logger.error("RuntimeException occurred: {}", ex.getMessage());
        ErrorResponseModel error = new ErrorResponseModel(
                request.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // General Exception handling
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseModel> handleGenericException(
            Exception ex,
            WebRequest request
    ) {
        logger.error("Exception occurred: {}", ex.getMessage());
        ErrorResponseModel error = new ErrorResponseModel(
                request.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred. Please try again later.",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
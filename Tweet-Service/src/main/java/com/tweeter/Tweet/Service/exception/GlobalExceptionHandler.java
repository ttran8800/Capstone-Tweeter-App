package com.tweeter.Tweet.Service.exception;

import com.tweeter.Tweet.Service.response.ErrorResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    public ResponseEntity<ErrorResponse> handCustomException(CustomException customException) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(customException.getMessage())
                .details(customException.getErrorCode())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(customException.getStatus()));
    }
}

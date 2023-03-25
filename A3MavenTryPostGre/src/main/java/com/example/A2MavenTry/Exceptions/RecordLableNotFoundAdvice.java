package com.example.A2MavenTry.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RecordLableNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(RecordLableNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String recordLblNotFoundHandler(RecordLableNotFoundException ex)
    {
        return ex.getMessage();
    }
}

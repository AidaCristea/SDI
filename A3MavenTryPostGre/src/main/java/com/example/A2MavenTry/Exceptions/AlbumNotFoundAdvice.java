package com.example.A2MavenTry.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class AlbumNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(GroupNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String AlbumNotFoundHandler(AlbumNotFoundException ex)
    {
        return ex.getMessage();
    }

}

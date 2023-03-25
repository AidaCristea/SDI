package com.example.A2MavenTry.Exceptions;

public class SingerNotFoundException extends RuntimeException{
    public SingerNotFoundException(Integer id)
    {
        super("Could not find singer" + id);
    }

}

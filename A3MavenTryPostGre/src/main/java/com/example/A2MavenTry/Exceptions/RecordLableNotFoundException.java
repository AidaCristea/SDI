package com.example.A2MavenTry.Exceptions;



public class RecordLableNotFoundException extends RuntimeException{
    public RecordLableNotFoundException(Integer id)
    {
        super("Could not find record lable " + id);
    }
}

package com.example.A2MavenTry.Exceptions;

public class GroupNotFoundException extends RuntimeException{
    public GroupNotFoundException(Integer id)
    {
        super("Could not find group" + id);
    }
}

package com.example.A2MavenTry.Exceptions;

import jakarta.persistence.criteria.CriteriaBuilder;

public class AlbumNotFoundException extends RuntimeException{
    public AlbumNotFoundException(Integer id){
        super("Could not find album" + id);
    }
}

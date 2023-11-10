package com.example.homeworkweb.exceptions;

public class BlogNotFoundException extends RuntimeException{

    public BlogNotFoundException(String message) {
        super(message);
    }
}

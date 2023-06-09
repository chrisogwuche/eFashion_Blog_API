package com.decagon.fashionBlog.exceptions;

public class PostNotFoundExceptions extends RuntimeException{

    public PostNotFoundExceptions() {
        super();
    }

    public PostNotFoundExceptions(String message) {
        super(message);
    }

}

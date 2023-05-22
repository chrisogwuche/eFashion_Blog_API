package com.decagon.fashionBlog.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class GlobalExceptionsHandler {

    @ExceptionHandler
    public ResponseEntity<BlogErrorResponse> genericHandler(Exception ex, HttpServletRequest request){
        BlogErrorResponse errorResponse = new BlogErrorResponse(
                ZonedDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                request.getRequestURI(),
                ex.getMessage()
        );

        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = PostNotFoundExceptions.class)
    public ResponseEntity<BlogErrorResponse> postNotFoundHandler(PostNotFoundExceptions ex, HttpServletRequest request){
        BlogErrorResponse errorResponse = new BlogErrorResponse(
                ZonedDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                request.getRequestURI(),
                ex.getMessage());

        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(value = UsersNotFoundExceptions.class)
    public ResponseEntity<BlogErrorResponse> userNotFoundHandler(UsersNotFoundExceptions ex, HttpServletRequest request){
        BlogErrorResponse errorResponse = new BlogErrorResponse(
                ZonedDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                request.getRequestURI(),
                ex.getMessage());

        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = NoPermissionExceptions.class)
    public ResponseEntity<BlogErrorResponse> NoPermissionHandler(NoPermissionExceptions ex, HttpServletRequest request){
        BlogErrorResponse errorResponse = new BlogErrorResponse(
                ZonedDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                request.getRequestURI(),
                ex.getMessage());

        return new ResponseEntity<>(errorResponse,HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = UnAuthorizedExceptions.class)
    public ResponseEntity<BlogErrorResponse> UnAuthorizedHandler(UnAuthorizedExceptions ex, HttpServletRequest request){
        BlogErrorResponse errorResponse = new BlogErrorResponse(
                ZonedDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                request.getRequestURI(),
                ex.getMessage());

        return new ResponseEntity<>(errorResponse,HttpStatus.UNAUTHORIZED);
    }

}

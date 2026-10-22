package org.example.library.exception;

/**
 * @author fulihua
 * @date 2026/8/19 3:10 PM
 * @description
 */
public class BookNotFoundException extends RuntimeException{
    public BookNotFoundException(String message){
        super(message);
    }
}
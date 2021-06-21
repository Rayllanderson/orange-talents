package com.rayllanderson.forum.exceptions;

public class NaoEncontradoException extends RuntimeException{
    public NaoEncontradoException(String message) {
        super(message);
    }
}

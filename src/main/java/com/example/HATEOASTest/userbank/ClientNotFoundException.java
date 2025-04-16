package com.example.HATEOASTest.userbank;

public class ClientNotFoundException extends RuntimeException{
    public ClientNotFoundException(Long id){super("Could not find client " + id);}
}

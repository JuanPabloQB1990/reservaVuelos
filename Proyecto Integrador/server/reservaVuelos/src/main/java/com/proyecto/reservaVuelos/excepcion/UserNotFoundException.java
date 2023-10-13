package com.proyecto.reservaVuelos.excepcion;

public class UserNotFoundException extends Exception{
    public UserNotFoundException(String message) {
        super(message);
    }
}

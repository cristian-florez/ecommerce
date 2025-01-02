package com.ecommerce.DTO;

//clase que representa la respuesta de autenticacion de un usuario
public class AuthoResponse {

    private String token;

    public AuthoResponse(){}

    public AuthoResponse(String token){
        this.token = token;
    }

    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token = token;
    }
}

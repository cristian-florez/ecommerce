package com.ecommerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.DTO.AuthenticationRequest;
import com.ecommerce.DTO.AuthoResponse;
import com.ecommerce.DTO.RegisterRequest;
import com.ecommerce.services.UserService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService; 

    //endpoint para registrar un usuario, utiliza el parametro request de tipo RegisterRequest y retorna un objeto de tipo AuthoResponse, dando como respuesta el token del usuario
    @PostMapping("/registrer")
    public ResponseEntity<AuthoResponse> registrer(@RequestBody RegisterRequest request){
        AuthoResponse response = userService.registrer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response); 
        
    }

    //endpoint para autenticar un usuario, utiliza el parametro request de tipo AuthenticationRequest y retorna un objeto de tipo AuthoResponse, dando como respuesta el token del usuario
    @PostMapping("/authenticate")
    public ResponseEntity<AuthoResponse> authenticate(
        @RequestBody AuthenticationRequest request, 
        HttpServletResponse response){
            AuthoResponse authoResponse = userService.authenticate(request);
            return ResponseEntity.ok(authoResponse);

        }
}

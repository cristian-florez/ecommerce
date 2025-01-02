package com.ecommerce.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.DTO.AuthenticationRequest;
import com.ecommerce.DTO.AuthoResponse;
import com.ecommerce.DTO.RegisterRequest;
import com.ecommerce.models.DetailsUser;
import com.ecommerce.models.Role; 
import com.ecommerce.models.RoleEnum;
import com.ecommerce.models.User;
import com.ecommerce.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired 
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    //metodo que registra un usuario, recibe un objeto de tipo RegisterRequest y retorna un objeto de tipo AuthoResponse, este metodo nos permite registrar un usuario en la base de datos y generar un token para el usuario registrado
    public AuthoResponse registrer(RegisterRequest registerRequest){

        try {
            User user = new User();

            //creamos un objeto de la clase Role para asignar el rol al usuario, si el usuario es admin se le asigna el rol de ADMIN, si no se le asigna el rol de CLIENT
            Role rol = new Role();
            rol.setName((registerRequest.getIsAdmin() == true)?
            RoleEnum.ADMIN : RoleEnum.CLIENT);
    
            //asignamos los valores al usuario y lo guardamos en la base de datos
            user.setName(registerRequest.getName());
            user.setEmail(registerRequest.getEmail());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            user.setRole(rol);
            user.setCreatedAt(LocalDate.now());
            
            userRepository.save(user);
    
            //generamos el token del usuario
            String jwtToken = jwtService.generateToken(new DetailsUser(user));
    
            //retornamos el token del usuario
            return new AuthoResponse(jwtToken);
        } catch (Exception e) {
            throw new RuntimeException("Error registring user");
        }

    }

    //metodo que autentica un usuario, recibe un objeto de tipo AuthenticationRequest y retorna un objeto de tipo AuthoResponse, este metodo nos permite autenticar un usuario en el sistema y generar un token para el usuario autenticado
    public AuthoResponse authenticate(AuthenticationRequest request){

        try {
            //creamos un objeto de la clase UsernamePasswordAuthenticationToken para autenticar al usuario, este objeto recibe el email y la contraseña del usuario
            UsernamePasswordAuthenticationToken userAuthentication = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

            //autenticamos al usuario, userAuthentication es el objeto que contiene el email y la contraseña del usuario
            authenticationManager.authenticate(userAuthentication);

            //buscamos al usuario por su email, si el usuario no existe lanzamos una excepcion
            User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

            String jwtToken = jwtService.generateToken(new DetailsUser(user));

            //retornamos el token del usuario
            return new AuthoResponse(jwtToken);
        } catch (Exception e) {
            throw new BadCredentialsException("Error authenticating user");
        }
    }

}

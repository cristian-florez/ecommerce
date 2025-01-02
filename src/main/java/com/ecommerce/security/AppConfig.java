package com.ecommerce.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ecommerce.models.DetailsUser;
import com.ecommerce.models.User;
import com.ecommerce.repositories.UserRepository;

//esta clase es una clase de configuracion que se encarga de configurar la autenticacion de los usuarios, se crea ya que Spring Security no tiene soporte para la autenticacion de usuarios con JPA
@Configuration
public class AppConfig {

    @Autowired
    private UserRepository userRepository;

    //metodo que sobre-escribe el metodo loadUserByUsername de la interfaz UserDetailsService
    @Bean
    public UserDetailsService userDetailsService(){

        return new UserDetailsService() {
            
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

                //utilizamos el metodo findByEmail de la interfaz UserRepository para buscar un usuario por su email que sera el username
                User user = userRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("user not found"));
        
                return new DetailsUser(user);
            }
        };
    } 

    //metodo que sobre-escribe el metodo authenticationProvider de la interfaz AuthenticationProvider
    @Bean
    public AuthenticationProvider authenticationProvider(){

        //creamos un objeto de la clase DaoAuthenticationProvider para autenticar a los usuarios
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        //utilizamos el metodo userDetailsService para obtener los detalles del usuario
        authenticationProvider.setUserDetailsService(userDetailsService());
        //utilizamos el metodo passwordEncoder para encriptar la contraseña que se compara con la contraseña encriptada en la base de datos
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        //retornamos el objeto authenticationProvider con los detalles del usuario y la contraseña encriptada
        return authenticationProvider;
    }

    //metodo que sobre-escribe el metodo passwordEncoder de la interfaz PasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder(){
        //utilizamos el metodo de la clase BCryptPasswordEncoder para encriptar la contraseña
        return new BCryptPasswordEncoder();
    }

    //metodo que sobre-escribe el metodo authenticationManager de la interfaz AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config){
        //con el objeto config obtenemos el AuthenticationManager para autenticar a los usuarios
        try {
            return config.getAuthenticationManager();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

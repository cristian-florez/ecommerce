package com.ecommerce.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private AuthenticationProvider authenticationProvider;


    //metodo que sobre-escribe el metodo securityFilterChain de la interfaz SecurityFilterChain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //configuramos el csrf para deshabilitarlo ya que no se utilizara en la aplicacion, el csrf es un mecanismo de seguridad que se utiliza para prevenir ataques de falsificacion de solicitudes entre sitios
        http.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
        .requestMatchers("/login/**").permitAll()
        .requestMatchers("/products/**").authenticated()
        .anyRequest().authenticated())
        .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))//configuramos la politica de sesion para que sea sin estado lo que significa que no se guardara la sesion del usuario en el servidor
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);//agregamos el filtro jwtFilter antes del filtro UsernamePasswordAuthenticationFilter

        return http.build();
    }

}
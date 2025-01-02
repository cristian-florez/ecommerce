package com.ecommerce.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ecommerce.services.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{

    @Autowired
    private  UserDetailsService userDetailsService;

    @Autowired
    private  JwtService jwtService;

    //metodo que sobre-escribe el metodo doFilterInternal de la clase OncePerRequestFilter para filtrar las peticiones y validar el token
    @Override
    protected void doFilterInternal(
        //los parametros que recibe son la peticion, la respuesta y la cadena de filtros
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain)
            throws ServletException, IOException {
    
        try {
            // Extraer el encabezado de autorización
            String authHeader = request.getHeader("Authorization");
            String jwt = null;
            String username = null;
    
            // Validar el encabezado y extraer el token
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                jwt = authHeader.substring(7); // Extraer el token después de "Bearer "
                username = jwtService.getUsername(jwt); // Extraer el nombre de usuario del token
            }
    
            // Validar si se obtuvo un nombre de usuario y si no hay autenticación en el contexto de seguridad
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                // Cargar los detalles del usuario y validar el token
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
    
                if (jwtService.validateToken(jwt, userDetails)) {

                    // Crear un objeto de autenticación y establecer los detalles de la solicitud
                    UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    
                    // Establecer el contexto de seguridad que es los detalles del usuario autenticado
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: " + e.getMessage());
            return;
        }
    
        // después de la autenticación, continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }

}

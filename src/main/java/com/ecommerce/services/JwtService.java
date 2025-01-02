package com.ecommerce.services;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    //clave secreta que se utiliza para codificar y decodificar el token
    private static final String SECRET_KEY = "tdGzVAJRgT7XM5PHNH0X4X/M34pkMe8/9IS3zehOuHU=";

    //metodo que genera un token con los detalles del usuario
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    //metodo que tambien genera un token con los detalles del usuario pero ademas recibe un mapa con informacion adicional que se puede agregar al token
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails){
 
        //configuramos la fecha de emision y de expiracion del token
        LocalDateTime issuedAt = LocalDateTime.now();
        LocalDateTime expiration = issuedAt.plusHours(1);

        //creamos un objeto de la clase HashMap para agregar los detalles del usuario y la informacion adicional al token
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(convertToDate(issuedAt))
                .setExpiration(convertToDate(expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)//firmamos el token con la clave secreta
                .compact();
    }

    //metodo que convierte un objeto de la clase LocalDateTime a un objeto de la clase Date
    private Date convertToDate(LocalDateTime localDateTime){
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    //metodo que obtiene el nombre de usuario del token
    public String getUsername(String token){
        return getClaim(token, Claims::getSubject);
    }

    //metodo que obtiene un reclamo del token, que es un objeto que contiene informacion adicional del token como el nombre de usuario, la fecha de emision y de expiracion
    public <T> T getClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //metodo que obtiene todos los reclamos del token, que es un objeto que contiene informacion adicional del token como el nombre de usuario, la fecha de emision y de expiracion se diferencia del metodo getClaim ya que este metodo retorna todos los reclamos del token y el otro solo retorna un reclamo
    private Claims getAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    //metodo que obtiene la clave secreta para firmar el token
    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //metodo que valida el token, recibe el token y los detalles del usuario y retorna un valor booleano que indica si el token es valido o no
    public boolean validateToken(String token, UserDetails userDetails){
        String username = getUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    //metodo que valida si el token ha expirado, recibe el token y retorna un valor booleano que indica si el token ha expirado o no
    private boolean isTokenExpired(String token){
        return getExpiration(token).before(new Date());
    }

    //metodo que obtiene la fecha de expiracion del token para pasarselo al metodo isTokenExpired
    private Date getExpiration(String token){
        return (Date) getClaim(token,Claims::getExpiration);
    }

}

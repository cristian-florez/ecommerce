package com.ecommerce.models;


import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

//esta clase se encarga de obtener los detalles del usuario que se autentica en el sistema, sobreescribiendo los metodos de la interfaz UserDetails para obtener el nombre de usuario, la contraseña y los roles del usuario
//se usa en UserService para obtener los detalles del usuario y en appConfig para sobreescribir el metodo de la interfaz UserDetailsService
public class DetailsUser implements UserDetails{

    private User user;

    public DetailsUser(User user){
        this.user = user;
    }

    //metodo que sobreescribe el metodo getUsername de la interfaz UserDetails para obtener el nombre de usuario
    @Override
    public String getUsername(){
        return user.getEmail();
    }

    //metodo que sobreescribe el metodo getPassword de la interfaz UserDetails para obtener la contraseña del usuario
    @Override
    public String getPassword(){
        return user.getPassword();
    }


    //metodo que sobreescribe el metodo getAuthorities de la interfaz UserDetails para obtener los roles del usuario
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){

        //obtenemos el rol del usuario
        RoleEnum roleEnum = user.getRole().getName();

        //creamos un objeto de la clase SimpleGrantedAuthority para obtener el rol del usuario en forma de autoridad
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(roleEnum.name());

        return List.of(authority);
    }

    //metodos que sobreescriben los metodos de la interfaz UserDetails que no se utilizan
    @Override
    public boolean isAccountNonExpired() {
		return true;
	}

    @Override
	public boolean isAccountNonLocked() {
		return true;
	}

    @Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

    @Override
	public boolean isEnabled() {
		return true;
	}

}

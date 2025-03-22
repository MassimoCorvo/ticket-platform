package com.ticket_platform.ticket_platform.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ticket_platform.ticket_platform.model.Ruolo;
import com.ticket_platform.ticket_platform.model.Utente;

public class DatabaseUserDetails implements UserDetails{
    private final Integer id;
    private final String email;
    private final String password;
    private final Set<GrantedAuthority> authorities;
    
    public DatabaseUserDetails(Utente utente){
        this.id = utente.getId();
        this.email = utente.getEmail();
        this.password = utente.getPassword();

        this.authorities = new HashSet<GrantedAuthority>();
        for (Ruolo ruolo : utente.getRuoli()){
            this.authorities.add(new SimpleGrantedAuthority(ruolo.getRuolo()));
        }

    }

    public Integer getId(){
        return this.id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
    
}

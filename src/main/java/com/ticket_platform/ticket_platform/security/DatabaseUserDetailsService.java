package com.ticket_platform.ticket_platform.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ticket_platform.ticket_platform.model.Utente;
import com.ticket_platform.ticket_platform.repository.UtenteRepository;

public class DatabaseUserDetailsService implements UserDetailsService{

    @Autowired
    UtenteRepository utenteRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Utente> userAttempt = utenteRepository.findByEmail(username);

        if (userAttempt.isPresent()){
            return new DatabaseUserDetails(userAttempt.get());
        } else {
            throw new UsernameNotFoundException("Nessun utente con questa email è stato trovato.");
        }
    }

}
    


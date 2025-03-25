package com.ticket_platform.ticket_platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ticket_platform.ticket_platform.model.Utente;
import com.ticket_platform.ticket_platform.repository.UtenteRepository;

@Service
public class UtenteService {
    @Autowired
    UtenteRepository utenteRepository;

    public void update(Utente utente){
        utenteRepository.save(utente);
    }

    public Utente utenteAutenticato() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return utenteRepository.findByEmail(email).get();
    }

}

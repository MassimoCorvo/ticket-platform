package com.ticket_platform.ticket_platform.service;

import org.springframework.beans.factory.annotation.Autowired;
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

}

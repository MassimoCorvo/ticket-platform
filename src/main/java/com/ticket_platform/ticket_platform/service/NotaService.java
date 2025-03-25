package com.ticket_platform.ticket_platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticket_platform.ticket_platform.model.Nota;
import com.ticket_platform.ticket_platform.repository.NotaRepository;

@Service
public class NotaService {
    @Autowired
    NotaRepository notaRepository;

    public Nota create(Nota notaDaCreare){
        return notaRepository.save(notaDaCreare);
    }
}

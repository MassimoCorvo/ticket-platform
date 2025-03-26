package com.ticket_platform.ticket_platform.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticket_platform.ticket_platform.model.Ticket;
import com.ticket_platform.ticket_platform.model.Utente;
import com.ticket_platform.ticket_platform.repository.TicketRepository;
import com.ticket_platform.ticket_platform.repository.UtenteRepository;

@Service
public class TicketService {
    
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    public Ticket getById(Integer id){
        Optional<Ticket> ticket = ticketRepository.findById(id);
        if (ticket.isEmpty()){
            throw new IllegalArgumentException("Ticket non trovato con id " + id);
        }

        
        return ticket.get();
    }

    public List<Ticket> findAll(){
        return ticketRepository.findAll();
    }

    public List<Ticket> trovaTuttiTicketUtente(Integer id){
        List<Ticket> allTickets = ticketRepository.findAll();
        List<Ticket> ticketsUtente = new ArrayList<Ticket>();
        for(Ticket ticket : allTickets){
            if(ticket.getUtente().getId() == id)
                ticketsUtente.add(ticket);
        } 
        return ticketsUtente;
    }

    public Optional<Ticket> findById(Integer id){
        return ticketRepository.findById(id);
    }

    public Ticket create(Ticket ticketDaCreare){
        ticketDaCreare.setDataDiCreazione(LocalDateTime.now());
        ticketDaCreare.setStato("da fare");
        
        return ticketRepository.save(ticketDaCreare);
    }

    public Ticket update(Ticket ticket){
        return ticketRepository.save(ticket);
    }

    public void delete(Integer id){
        ticketRepository.delete(ticketRepository.findById(id).get());
    }

    public List<Ticket> findByTitle(String titolo) {
        return ticketRepository.findByTitoloContaining(titolo);
    }

    public List<Ticket> ricercaPerCategoria(String categoria){
        return ticketRepository.findByCategoriaNomeContaining(categoria);
    }

    public List<Ticket> ricercaPerStato(String stato){
        return ticketRepository.findByStato(stato);
    }

}
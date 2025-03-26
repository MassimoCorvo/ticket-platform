package com.ticket_platform.ticket_platform.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ticket_platform.ticket_platform.model.Ticket;
import com.ticket_platform.ticket_platform.service.TicketService;

@RestController
@CrossOrigin
@RequestMapping("/api/tickets")
public class TicketRestController {

    @Autowired
    TicketService ticketService;

    @GetMapping
    public List<Ticket> index(){
        return ticketService.findAll();
    }

    @GetMapping("/categoria")
    public List<Ticket> ricercaPerCategoria(@RequestParam String categoria){
        return ticketService.ricercaPerCategoria(categoria);
    }

    @GetMapping("/stato")
    public List<Ticket> ricercaPerStato(@RequestParam String stato){
        return ticketService.ricercaPerStato(stato);
    }

    

}

package com.ticket_platform.ticket_platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ticket_platform.ticket_platform.model.Utente;
import com.ticket_platform.ticket_platform.repository.TicketRepository;
import com.ticket_platform.ticket_platform.service.TicketService;
import com.ticket_platform.ticket_platform.service.UtenteService;


@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UtenteService utenteService;

    @GetMapping
    public String index(Model model, Authentication authentication){
        Utente utenteLoggato = utenteService.utenteAutenticato();
        model.addAttribute("utente", utenteLoggato);
        if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")))
            model.addAttribute("tickets", ticketService.findAll());
        else{
            model.addAttribute("tickets", ticketService.trovaTuttiTicketUtente(utenteLoggato.getId()));
        }
        return "index";
    }
}


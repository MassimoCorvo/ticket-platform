package com.ticket_platform.ticket_platform.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ticket_platform.ticket_platform.model.Ticket;
import com.ticket_platform.ticket_platform.repository.TicketRepository;
import com.ticket_platform.ticket_platform.repository.UtenteRepository;
import com.ticket_platform.ticket_platform.security.DatabaseUserDetails;
import com.ticket_platform.ticket_platform.service.TicketService;


@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private TicketRepository repository;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UtenteRepository utenteRepository;

    @GetMapping
    public String index(Model model, Authentication authentication, @AuthenticationPrincipal DatabaseUserDetails userDetails){
        if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")))
            model.addAttribute("tickets", ticketService.findAll());
        else{
            model.addAttribute("tickets", ticketService.trovaTuttiTicketUtente(userDetails.getId()));
        }
        model.addAttribute("utente", utenteRepository.findById(userDetails.getId()).get());
        return "index";
    }
}


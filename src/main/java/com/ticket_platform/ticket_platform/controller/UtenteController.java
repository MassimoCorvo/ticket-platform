package com.ticket_platform.ticket_platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ticket_platform.ticket_platform.model.Utente;
import com.ticket_platform.ticket_platform.repository.UtenteRepository;
import com.ticket_platform.ticket_platform.security.DatabaseUserDetails;

@Controller
@RequestMapping("/utente")
public class UtenteController {
    @Autowired
    UtenteRepository utenteRepository;

    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model,
            @AuthenticationPrincipal DatabaseUserDetails userDetails) {
        model.addAttribute("utente", utenteRepository.findById(id).get());

        if (id.equals(userDetails.getId())) {
            model.addAttribute("utente", utenteRepository.findById(id).get());
            return "dati_personali";
        }

        return "redirect:/";
    }

    @GetMapping("/{id}/modifica-dati")
    public String edit(@PathVariable Integer id, Model model,
            @AuthenticationPrincipal DatabaseUserDetails userDetails) {
        model.addAttribute("utente", utenteRepository.findById(id).get());

        if (id.equals(userDetails.getId())) {
            return "modifica_dati_personali";
        }
        
        return "redirect:/";
    }

    @PostMapping("/{id}/modifica-dati")
    public String update(@PathVariable Integer id, Model model, @ModelAttribute("utente") Utente utente,
            @AuthenticationPrincipal DatabaseUserDetails userDetails, BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {

            return "edit";
        }

        utente.setPassword("{noop}" + utente.getPassword());
        utenteRepository.save(utente);
        redirectAttributes.addFlashAttribute("message",
                String.format("Utente n° %s modificato con successo", utente.getId()));
        redirectAttributes.addFlashAttribute("messageClass", "alert-primary");
        return "redirect:/utente/" + id;
    }
}

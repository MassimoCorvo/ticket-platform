package com.ticket_platform.ticket_platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.ticket_platform.ticket_platform.service.UtenteService;

@Controller
@RequestMapping("/utente")
public class UtenteController {
    @Autowired
    UtenteRepository utenteRepository;

    @Autowired
    UtenteService utenteService;

    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model) {
        model.addAttribute("utente", utenteService.utenteAutenticato());

        if (id.equals(utenteService.utenteAutenticato().getId())) {
            model.addAttribute("utente", utenteRepository.findById(id).get());
            return "dati_personali";
        }

        return "redirect:/";
    }

    @GetMapping("/{id}/modifica-dati")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("utente", utenteService.utenteAutenticato());

        if (id.equals(utenteService.utenteAutenticato().getId())) {
            return "modifica_dati_personali";
        }
        
        return "redirect:/";
    }

    @PostMapping("/{id}/modifica-dati")
public String update(@PathVariable Integer id, Model model,
                     @ModelAttribute("utente") Utente utenteForm,
                     BindingResult bindingResult,
                     RedirectAttributes redirectAttributes) {

    if (bindingResult.hasErrors()) {
        return "modifica_dati_personali";
    }

    Utente utenteDB = utenteRepository.findById(id).orElseThrow();

    utenteDB.setNome(utenteForm.getNome());
    utenteDB.setCognome(utenteForm.getCognome());
    utenteDB.setEmail(utenteForm.getEmail());

    if (utenteForm.getPassword() != null && !utenteForm.getPassword().isBlank()) {
        utenteDB.setPassword("{noop}" + utenteForm.getPassword());
    }

    utenteRepository.save(utenteDB);

    redirectAttributes.addFlashAttribute("message",
            String.format("Utente n° %s modificato con successo", utenteDB.getId()));
    redirectAttributes.addFlashAttribute("messageClass", "alert-primary");

    return "redirect:/utente/" + id;
}

    @GetMapping("/{id}/modifica-stato")
    public String modificaStato(@PathVariable Integer id, Model model ){
        model.addAttribute("utente", utenteService.utenteAutenticato());
        return "cambia_stato_operatore";
    }

    @PostMapping("/{id}/modifica-stato")
    public String updateStato(@PathVariable Integer id, Model model, @ModelAttribute("utente") Utente utente,
    RedirectAttributes redirectAttributes){
        Utente utenteLoggato = utenteService.utenteAutenticato();
        Integer utenteId = utenteLoggato.getId();
        if(utenteService.puoDiventareNonAttivo(utenteId)){   
                utenteLoggato.setStato(utente.isStato());
                utenteService.update(utenteLoggato);
                redirectAttributes.addFlashAttribute("message",
                "Stato modificato con successo");
                redirectAttributes.addFlashAttribute("messageClass", "alert-primary");
                return "redirect:/";
            }
        
        redirectAttributes.addFlashAttribute("message",
            "L'utente non può cambiare stato");
        redirectAttributes.addFlashAttribute("messageClass", "alert-warning");
        return "redirect:/";
    }
}

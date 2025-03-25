package com.ticket_platform.ticket_platform.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.ticket_platform.ticket_platform.model.Nota;
import com.ticket_platform.ticket_platform.model.Ticket;
import com.ticket_platform.ticket_platform.model.Utente;
import com.ticket_platform.ticket_platform.repository.UtenteRepository;
import com.ticket_platform.ticket_platform.service.CategoriaService;
import com.ticket_platform.ticket_platform.service.NotaService;
import com.ticket_platform.ticket_platform.service.TicketService;
import com.ticket_platform.ticket_platform.service.UtenteService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private NotaService notaService;

    @Autowired
    private UtenteService utenteService;

    @GetMapping
    public String index(Model model, Authentication authentication) {
        Utente utenteLoggato = utenteService.utenteAutenticato();

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")))
            model.addAttribute("tickets", ticketService.findAll());
        else {
            model.addAttribute("tickets", ticketService.trovaTuttiTicketUtente(utenteLoggato.getId()));
        }

        model.addAttribute("utente", utenteLoggato);
        return "index";
    }

    @GetMapping("/create")
    public String create(Model model, Authentication authentication) {
        model.addAttribute("utente", utenteService.utenteAutenticato());
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("operatoriDisponibili", utenteRepository.findByStatoTrue());
        model.addAttribute("categorie", categoriaService.findAll());
        return "admin/create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("ticket") Ticket formTicket,
            BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("create", true);
            model.addAttribute("categorie", categoriaService.findAll());
            return "admin/create";
        }

        ticketService.create(formTicket);
        redirectAttributes.addFlashAttribute("message",
                String.format("Ticket n° %s creato con successo", formTicket.getId()));
        redirectAttributes.addFlashAttribute("messageClass", "alert-primary");
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model) {
        model.addAttribute("utente", utenteService.utenteAutenticato());
        Ticket ticket = ticketService.findById(id).get();
        model.addAttribute("ticket", ticket);
        return "show";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("utente", utenteService.utenteAutenticato());
        model.addAttribute("ticket", ticketService.getById(id));
        model.addAttribute("categorie", categoriaService.findAll());
        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("ticket") Ticket formTicket,
            BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("categorie", categoriaService.findAll());
            return "edit";
        }

        ticketService.update(formTicket);
        redirectAttributes.addFlashAttribute("message",
                String.format("Ticket n° %s modificato con successo", formTicket.getId()));
        redirectAttributes.addFlashAttribute("messageClass", "alert-primary");

        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {

        ticketService.delete(id);
        redirectAttributes.addFlashAttribute("message", String.format("Ticket n° %s eliminato con successo", id));
        redirectAttributes.addFlashAttribute("messageClass", "alert-danger");
        return "redirect:/";
    }

    @GetMapping("/search/title")
    public String findByTitle(@RequestParam(name = "title") String title, Model model) {
        model.addAttribute("utente", utenteService.utenteAutenticato());
        
        List<Ticket> tickets = ticketService.findByTitle(title);
        model.addAttribute("tickets", tickets);
        return "index";
    }

    @GetMapping("/nota/crea/{id}")
    public String create(@PathVariable Integer id, Model model, Authentication authentication) {
        Utente utenteLoggato = utenteService.utenteAutenticato();
        Integer idUtenteTicket = ticketService.findById(id).get().getUtente().getId();
        model.addAttribute("utente", utenteService.utenteAutenticato());

        //Controllo che l'utente loggato sia l'autore del ticket oppure l'admin
        if(utenteLoggato.getId().equals(idUtenteTicket) || authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))){   
            Nota nota = new Nota();
            model.addAttribute("nota", nota);
            model.addAttribute("ticket", ticketService.findById(id).get());
            return "aggiungi_nota";
        }
        
        //In caso contrario rimando alla lista dei ticket
        return "redirect:/tickets";
    }

    @PostMapping("/nota/crea/{id}")
    public String store(@PathVariable Integer id, @Valid @ModelAttribute("nota") Nota notaForm, Model model,
            RedirectAttributes redirectAttributes) {
        
        notaForm.setDataDiCreazione(LocalDateTime.now());
        notaForm.setAutore(utenteService.utenteAutenticato());
        notaForm.setTicket(ticketService.getById(id));

        notaService.create(notaForm);
        redirectAttributes.addFlashAttribute("message", "Nota aggiunta con successo.");
        redirectAttributes.addFlashAttribute("messageClass", "alert-primary");
        return "redirect:/tickets/" + id;
    }

    @GetMapping("/{id}/cambia-stato")
    public String editStato(@PathVariable Integer id, Model model){
        model.addAttribute("utente", utenteService.utenteAutenticato());
        model.addAttribute("ticket", ticketService.getById(id));
        return "cambia_stato_ticket";
    }

    @PostMapping("/{id}/cambia-stato")
    public String cambiaStato(@PathVariable Integer id, @ModelAttribute Ticket formTicket, Model model, RedirectAttributes
    redirectAttributes){

        Ticket ticketDaModificare = ticketService.getById(id);
        ticketDaModificare.setStato(formTicket.getStato());
        ticketService.update(ticketDaModificare);

        redirectAttributes.addFlashAttribute("message", "Stato modificato con successo.");
        redirectAttributes.addFlashAttribute("messageClass", "alert-primary");
        return "redirect:/tickets/" + id;
    }
}

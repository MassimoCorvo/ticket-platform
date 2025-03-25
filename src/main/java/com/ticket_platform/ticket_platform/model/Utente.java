package com.ticket_platform.ticket_platform.model;


import java.util.List;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "utenti")
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Password cannot be blank, empty or null")
    private String password;

    @OneToMany(mappedBy = "utente")
    private List<Ticket> tickets;

    @OneToMany(mappedBy = "autore")
    private List<Nota> note;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "ruolo_utente",
        joinColumns = @JoinColumn(name = "utente_id"),
        inverseJoinColumns = @JoinColumn(name = "ruolo_id")
    )
    private Set<Ruolo> ruoli;
    
    private boolean stato;

    @NotBlank(message = "Email cannot be blank, empty or null")
    private String email;

    @NotBlank(message = "Name cannot be blank, empty or null")
    private String nome;

    @NotBlank(message = "Surname cannot be blank, empty or null")
    private String cognome;

    public boolean isStato() {
        return stato;
    }

    public void setStato(boolean stato) {
        this.stato = stato;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
    
    public Set<Ruolo> getRuoli() {
        return ruoli;
    }

    public void setRuoli(Set<Ruolo> ruoli) {
        this.ruoli = ruoli;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    @Override
    public String toString(){
        return this.nome + " " + this.cognome;
    }

    public List<Nota> getNote() {
        return note;
    }

    public void setNote(List<Nota> note) {
        this.note = note;
    }
}

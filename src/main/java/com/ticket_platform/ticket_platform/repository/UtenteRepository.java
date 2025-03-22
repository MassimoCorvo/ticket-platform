package com.ticket_platform.ticket_platform.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ticket_platform.ticket_platform.model.Utente;

public interface UtenteRepository extends JpaRepository<Utente, Integer>{
   public List<Utente> findByStatoTrue();

   public Optional<Utente> findByEmail(String email);
}
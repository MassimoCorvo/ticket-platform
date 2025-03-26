package com.ticket_platform.ticket_platform.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ticket_platform.ticket_platform.model.Ticket;
public interface TicketRepository extends JpaRepository<Ticket, Integer>{
    public List<Ticket> findByTitoloContaining(String titolo);

    public List<Ticket> findByCategoriaNomeContaining(String categoria);

    public List<Ticket> findByStato(String stato);
}

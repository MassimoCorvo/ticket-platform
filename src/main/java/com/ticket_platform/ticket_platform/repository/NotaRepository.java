package com.ticket_platform.ticket_platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ticket_platform.ticket_platform.model.Nota;

public interface NotaRepository extends JpaRepository<Nota, Integer>{
    
}

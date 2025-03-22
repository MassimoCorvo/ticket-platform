package com.ticket_platform.ticket_platform.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticket_platform.ticket_platform.model.Categoria;
import com.ticket_platform.ticket_platform.repository.CategoriaRepository;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRespository;

    public List<Categoria> findAll(){
        return categoriaRespository.findAll();
    }
}

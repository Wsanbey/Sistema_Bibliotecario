package com.example.SitemaBibliotecario.Repositorio;

import com.example.SitemaBibliotecario.Modelo.Papel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PapelRepositorio extends JpaRepository<Papel, Long> {
    Papel findByPapel(String papel);
}
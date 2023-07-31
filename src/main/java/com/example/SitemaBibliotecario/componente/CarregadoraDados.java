package com.example.SitemaBibliotecario.componente;

import com.example.SitemaBibliotecario.Modelo.Papel;
import com.example.SitemaBibliotecario.Repositorio.PapelRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class CarregadoraDados implements CommandLineRunner {

    @Autowired
    private PapelRepositorio papelRepository;

    @Override
    public void run(String... args) throws Exception {

        String[] papeis = {"ADMIN", "USER", "BIBLIOTECARIO"};

        for (String papelString : papeis) {
            Papel papel = papelRepository.findByPapel(papelString);
            if (papel == null) {
                papel = new Papel(papelString);
                papelRepository.save(papel);
            }
        }
    }
}

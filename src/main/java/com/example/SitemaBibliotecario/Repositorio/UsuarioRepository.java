package com.example.SitemaBibliotecario.Repositorio;

import com.example.SitemaBibliotecario.Modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public  interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByLogin(String login);
}

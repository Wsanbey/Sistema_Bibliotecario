package com.example.SitemaBibliotecario.Modelo;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Papel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String papel;
    @ManyToMany(mappedBy = "papeis", fetch = FetchType.EAGER)
    private List<Usuario> usuarios;

    public Papel() {
    }

    public Papel(String papel) {
        super();
        this.papel = papel;
    }

    public Long getId() {
        return id;
    }

    public String getPapel() {
        return papel;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPapel(String papel) {
        this.papel = papel;
    }
}

package com.example.SitemaBibliotecario.Controller;

import com.example.SitemaBibliotecario.Modelo.Papel;
import com.example.SitemaBibliotecario.Repositorio.PapelRepositorio;
import com.example.SitemaBibliotecario.Repositorio.UsuarioRepository;
import com.example.SitemaBibliotecario.Modelo.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired //AUTOMATIZANDO O "NEW"
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PapelRepositorio papelRepositorio;

    @GetMapping("/novo")
    public String adicionarUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "/publica-criar-usuario";
    }

    @PostMapping("/salvar")
    public String salvarUsuario(@Valid Usuario usuario, BindingResult result,
                                Model model, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "/publica-criar-usuario";
        }
        Usuario usr = usuarioRepository.findByLogin(usuario.getLogin());
        if (usr != null) {
            model.addAttribute("loginExiste", "Login já existe castrado");
            return "/publica-criar-usuario";
        }
        //BUSCAR O PAPEL BÁSICO DE USÚARIO
        Papel papel = papelRepositorio.findByPapel("USER");
        List<Papel> papeis = new ArrayList<Papel>();
        papeis.add(papel);
        usuario.setPapeis(papeis); //ASSOCIA O PAPEL DE USER AO USÚARIO

        usuarioRepository.save(usuario);
        attributes.addFlashAttribute("mensagem", "Usuário salvo com sucesso!");
        return "redirect:/usuario/novo";
    }

    @RequestMapping("/admin/listar")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "/auth/admin/admin-listar-usuario";
    }

    @GetMapping("/admin/apagar/{id}")
    public String deletarUser(@PathVariable("id") long id, Model model) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Id inválido:" + id));
        usuarioRepository.delete(usuario);
        return "redirect:/usuario/admin/listar";
    }

    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable("id") long id, Model model) {
        Optional<Usuario> usuarioVelho = usuarioRepository.findById(id);
        if (!usuarioVelho.isPresent()) {
            throw new IllegalArgumentException("Usuário inválido:" + id);
        }
        Usuario usuario = usuarioVelho.get();
        model.addAttribute("usuario", usuario);
        return "/auth/user/user-alterar-usuario";
    }

    @PostMapping("/editar/{id}")
    public String editarUsuario(@PathVariable("id") long id,
                                @Valid Usuario usuario, BindingResult result) {
        if (result.hasErrors()) {
            usuario.setId(id);
            return "/auth/user/user-alterar-usuario";
        }
        usuarioRepository.save(usuario);
        return "redirect:/usuario/admin/listar";
    }


}

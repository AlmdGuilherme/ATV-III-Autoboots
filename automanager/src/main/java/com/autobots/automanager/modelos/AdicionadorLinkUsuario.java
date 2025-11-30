package com.autobots.automanager.modelos;

import com.autobots.automanager.controle.UsuarioControle;
import com.autobots.automanager.entitades.Usuario;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdicionadorLinkUsuario implements AdicionadorLink<Usuario>{
    @Override
    public void adicionadorLinkGeral(List<Usuario> usuarios) {
        for (Usuario usuario: usuarios) {
            long id = usuario.getId();
            Link linkProprio = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder
                            .methodOn(UsuarioControle.class)
                            .buscarUsuarioPorId(id))
                    .withSelfRel();
            usuario.add(linkProprio);
        }
    }

    @Override
    public void adicionadorLink(Usuario usuario) {
        Link linkProprio = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(UsuarioControle.class)
                        .listarUsuarios())
                .withRel("usuarios");
        usuario.add(linkProprio);
    }
}

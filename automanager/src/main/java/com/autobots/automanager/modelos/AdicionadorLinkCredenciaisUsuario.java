package com.autobots.automanager.modelos;

import com.autobots.automanager.controle.CredencialControle;
import com.autobots.automanager.entitades.CredencialUsuarioSenha;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdicionadorLinkCredenciaisUsuario implements AdicionadorLink<CredencialUsuarioSenha> {
    @Override
    public void adicionadorLinkGeral(List<CredencialUsuarioSenha> credenciais) {
        for (CredencialUsuarioSenha credencialUsuarioSenha : credenciais) {
            long id = credencialUsuarioSenha.getId();
            Link linkProprio = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder
                            .methodOn(CredencialControle.class)
                            .buscarCredenciaisUsuariosId(id))
                    .withSelfRel();
            credencialUsuarioSenha.add(linkProprio.withSelfRel());
        }
    }

    @Override
    public void adicionadorLink(CredencialUsuarioSenha credencialUsuarioSenha) {
        Link linkProprio = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(CredencialControle.class)
                        .buscarCredenciaisUsuarios())
                .withRel("Credenciais usuários");
        credencialUsuarioSenha.add(linkProprio);
    }
}

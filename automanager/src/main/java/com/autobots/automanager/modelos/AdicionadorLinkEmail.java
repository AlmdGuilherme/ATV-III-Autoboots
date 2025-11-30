package com.autobots.automanager.modelos;

import com.autobots.automanager.controle.EmailControle;
import com.autobots.automanager.entitades.Email;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdicionadorLinkEmail implements AdicionadorLink<Email> {

    @Override
    public void adicionadorLinkGeral(List<Email> lista) {
        for (Email email : lista) {
            long id = email.getId();
            Link linkProprio = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder
                            .methodOn(EmailControle.class)
                            .findEmailById(id))
                    .withSelfRel();
            email.add(linkProprio);
        }
    }

    @Override
    public void adicionadorLink(Email email) {
        Link linkProprio = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(EmailControle.class)
                        .listarEmails())
                .withRel("emails");
        email.add(linkProprio);
    }
}

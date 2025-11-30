package com.autobots.automanager.modelos;

import com.autobots.automanager.controle.EnderecoControle;
import com.autobots.automanager.entitades.Endereco;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdicionadorLinkEnderecos implements AdicionadorLink<Endereco> {
    @Override
    public void adicionadorLinkGeral(List<Endereco> enderecos) {
        for (Endereco endereco : enderecos) {
            long id = endereco.getId();
            Link linkProprio = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder
                            .methodOn(EnderecoControle.class)
                            .findAddressById(id))
                    .withSelfRel();
            endereco.add(linkProprio);
        }
    }

    @Override
    public void adicionadorLink(Endereco endereco) {
        Link linkProprio = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(EnderecoControle.class)
                        .listarEnderecos())
                .withRel("Enderecos");
        endereco.add(linkProprio);
    }
}
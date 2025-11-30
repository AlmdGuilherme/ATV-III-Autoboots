package com.autobots.automanager.modelos;

import com.autobots.automanager.controle.TelefoneControle;
import com.autobots.automanager.entitades.Telefone;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdicionadorLinkTelefone implements AdicionadorLink<Telefone> {

    @Override
    public void adicionadorLinkGeral(List<Telefone> telefones) {
        for (Telefone telefone:  telefones){
            long id = telefone.getId();
            Link linkProprio = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder
                            .methodOn(TelefoneControle.class)
                            .buscarTelefonePorId(id))
                    .withSelfRel();
            telefone.add(linkProprio);
        }
    }

    @Override
    public void adicionadorLink(Telefone telefone) {
        Link linkProprio = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(TelefoneControle.class)
                        .listarTelefones())
                .withRel("Telefones");
        telefone.add(linkProprio);
    }

}

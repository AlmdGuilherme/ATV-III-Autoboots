package com.autobots.automanager.modelos;

import com.autobots.automanager.controle.MercadoriaControle;
import com.autobots.automanager.entitades.Mercadoria;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdicionadorLinkMercadoria implements AdicionadorLink<Mercadoria> {
    @Override
    public void adicionadorLinkGeral(List<Mercadoria> mercadorias) {
        for (Mercadoria mercadoria : mercadorias) {
            long id = mercadoria.getId();
            Link linkProprio = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder
                            .methodOn(MercadoriaControle.class)
                            .buscarMercadoriaPorId(id))
                    .withSelfRel();
            mercadoria.add(linkProprio);
        }
    }

    public void adicionadorLink(Mercadoria mercadoria) {
        Link linkProprio = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(MercadoriaControle.class)
                        .listarMercadoria())
                .withRel("mercadoria");
        mercadoria.add(linkProprio);
    }
}

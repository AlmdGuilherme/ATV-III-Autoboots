package com.autobots.automanager.modelos;

import com.autobots.automanager.controle.ServicoControle;
import com.autobots.automanager.entitades.Servico;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdicionadorLinkServico implements AdicionadorLink<Servico> {
    public void adicionadorLinkGeral(List<Servico> servicos) {
        for (Servico servico : servicos) {
            long id = servico.getId();
            Link linkProprio = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder
                            .methodOn(ServicoControle.class)
                            .buscarServicoPorId(id))
                    .withSelfRel();
            servico.add(linkProprio);
        }
    }

    @Override
    public void adicionadorLink(Servico servico) {
        Link linkProprio = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(ServicoControle.class)
                        .listarServicos())
                .withRel("Servicos");
        servico.add(linkProprio);
    }
}

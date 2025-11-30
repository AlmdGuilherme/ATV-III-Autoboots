package com.autobots.automanager.modelos;

import com.autobots.automanager.controle.VendaControle;
import com.autobots.automanager.entitades.Venda;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdicionadorLinkVenda implements AdicionadorLink<Venda> {

    @Override
    public void adicionadorLinkGeral(List<Venda> vendas) {
        for (Venda venda: vendas){
            long id = venda.getId();
            Link linkProprio = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder
                            .methodOn(VendaControle.class)
                            .buscarVendaPorId(id))
                    .withSelfRel();
            venda.add(linkProprio);
        }
    }

    @Override
    public void adicionadorLink(Venda venda) {
        Link linkProprio = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(VendaControle.class)
                        .listarVendas())
                .withRel("Vendas");
        venda.add(linkProprio);
    }
}

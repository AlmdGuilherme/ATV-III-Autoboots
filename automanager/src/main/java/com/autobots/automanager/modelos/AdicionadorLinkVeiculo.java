package com.autobots.automanager.modelos;

import com.autobots.automanager.controle.VeiculoControle;
import com.autobots.automanager.entitades.Veiculo;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdicionadorLinkVeiculo implements AdicionadorLink<Veiculo> {
    @Override
    public void adicionadorLinkGeral(List<Veiculo> veiculos) {
        for (Veiculo veiculo : veiculos) {
            long id = veiculo.getId();
            Link linkProprio = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder
                            .methodOn(VeiculoControle.class)
                            .buscarVeiculoPorId(id))
                    .withSelfRel();
            veiculo.add(linkProprio);
        }
    }

    @Override
    public void adicionadorLink(Veiculo veiculo) {
        Link linkProprio = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(VeiculoControle.class)
                        .listarVeiculos())
                .withRel("Veiculos");
        veiculo.add(linkProprio);
    }
}

package com.autobots.automanager.modelos;

import com.autobots.automanager.controle.EmpresaControle;
import com.autobots.automanager.entitades.Empresa;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdicionadorLinkEmpresa implements AdicionadorLink<Empresa> {

    @Override
    public void adicionadorLinkGeral(List<Empresa> lista) {
        for (Empresa empresa: lista) {
            long id = empresa.getId();
            Link linkProprio = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder
                            .methodOn(EmpresaControle.class)
                            .findyCompanyById(id))
                    .withSelfRel();
            empresa.add(linkProprio);
        }
    }

    @Override
    public void adicionadorLink(Empresa empresa) {
        Link linkProprio = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(EmpresaControle.class)
                        .listarEmpresas())
                .withRel("empressas");
        empresa.add(linkProprio);
    }
}

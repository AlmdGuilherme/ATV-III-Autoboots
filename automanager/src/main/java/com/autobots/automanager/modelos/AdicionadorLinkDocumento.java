package com.autobots.automanager.modelos;


import com.autobots.automanager.controle.DocumentoControle;
import com.autobots.automanager.entitades.Documento;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdicionadorLinkDocumento implements AdicionadorLink<Documento> {

    @Override
    public void adicionadorLinkGeral(List<Documento> lista) {
        for (Documento documento : lista) {
            long id  = documento.getId();
            Link linkProprio = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder
                            .methodOn(DocumentoControle.class)
                            .findDocById(id))
                    .withSelfRel();
            documento.add(linkProprio);
        }
    }

    @Override
    public void adicionadorLink(Documento documento) {
        Link linkProprio = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(DocumentoControle.class)
                        .listarDocumentos())
                .withRel("documentos");
        documento.add(linkProprio);

    }
}

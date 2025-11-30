package com.autobots.automanager.modelos;

import com.autobots.automanager.controle.CredencialControle;
import com.autobots.automanager.entitades.CredencialCodigoBarra;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdicionadorLinkCredencialCodigoBarras implements AdicionadorLink<CredencialCodigoBarra> {

    @Override
    public void adicionadorLinkGeral(List<CredencialCodigoBarra> credencial) {
        for (CredencialCodigoBarra c : credencial) {
            long id = c.getId();
            Link linkProprio = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder
                            .methodOn(CredencialControle.class)
                            .buscarCredenciaisCodigoBarraId(id))
                    .withSelfRel();
            c.add(linkProprio.withSelfRel());
        }
    }

    @Override
    public void adicionadorLink(CredencialCodigoBarra creds) {
        Link linkProprio = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(CredencialControle.class)
                        .buscarCredenciaisCodigoBarra())
                .withRel("Credenciais código de barras");
        creds.add(linkProprio);
    }

}

package com.autobots.automanager.modelos.selecionadores;

import com.autobots.automanager.entitades.Documento;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DocumentoSelecionador {
    public Documento selecionadorDocumento(List<Documento> documentos, long id) {
        Documento documentoSelecionado = null;
        for (Documento documento : documentos) {
            if (documento.getId() == id) {
                documentoSelecionado = documento;
            }
        }
        return documentoSelecionado;
    }
}

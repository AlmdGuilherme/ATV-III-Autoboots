package com.autobots.automanager.modelos.selecionadores;

import com.autobots.automanager.entitades.Telefone;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TelefoneSelecionador {
    public Telefone selecionadorTelefone(List<Telefone> telefones, long id) {
        Telefone telefoneSelecionado = null;
        for (Telefone telefone: telefones) {
            if (telefone.getId() == id) {
                telefoneSelecionado = telefone;
            }
         }
        return telefoneSelecionado;
    }
}


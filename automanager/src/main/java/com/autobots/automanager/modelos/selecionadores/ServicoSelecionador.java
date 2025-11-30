package com.autobots.automanager.modelos.selecionadores;

import com.autobots.automanager.entitades.Servico;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServicoSelecionador {
    public Servico selecionadorServico (List<Servico> servicos, long id) {
        Servico servicoSelecionado = null;
        for (Servico servico: servicos) {
            if (servico.getId() == id) {
                servicoSelecionado = servico;
            }
        }
        return servicoSelecionado;
    }
}

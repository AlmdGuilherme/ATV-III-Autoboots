package com.autobots.automanager.modelos.selecionadores;

import com.autobots.automanager.entitades.Mercadoria;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MercadoriaSelecionador {
    public Mercadoria selecionadorMercadoria(List<Mercadoria> mercadorias, long id) {
        Mercadoria mercadoriaSelecionada = null;
        for (Mercadoria mercadoria: mercadorias) {
            if (mercadoria.getId() == id) {
                mercadoriaSelecionada = mercadoria;
            }
        }
        return mercadoriaSelecionada;
    }
}

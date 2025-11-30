package com.autobots.automanager.modelos.selecionadores;

import com.autobots.automanager.entitades.Venda;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VendaSelecionador {
    public Venda selecionadorVenda(List<Venda> vendas, long id) {
        Venda vendaSelecionada = null;
        for (Venda venda : vendas) {
            if (venda.getId().equals(id)) {
                vendaSelecionada = venda;
            }
        }
        return vendaSelecionada;
    }
}

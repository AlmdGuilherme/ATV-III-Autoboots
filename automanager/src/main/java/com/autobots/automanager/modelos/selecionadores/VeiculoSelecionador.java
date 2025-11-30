package com.autobots.automanager.modelos.selecionadores;

import com.autobots.automanager.entitades.Veiculo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VeiculoSelecionador {
    public Veiculo selecionadorVeiculo(List<Veiculo> veiculos, long id){
        Veiculo veiculoSelecionado = null;
        for (Veiculo veiculo: veiculos) {
            if(veiculo.getId()==id){
                veiculoSelecionado = veiculo;
            }
        }
        return veiculoSelecionado;
    }
}

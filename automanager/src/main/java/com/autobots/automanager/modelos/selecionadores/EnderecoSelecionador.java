package com.autobots.automanager.modelos.selecionadores;

import com.autobots.automanager.entitades.Endereco;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EnderecoSelecionador {
    public Endereco selecionadorEndereco(List<Endereco> enderecos, long id) {
        Endereco enderecoSelecionado = null;
        for (Endereco endereco: enderecos){
            if (endereco.getId() == id){
                enderecoSelecionado = endereco;
            }
        }
        return enderecoSelecionado;
    }
}

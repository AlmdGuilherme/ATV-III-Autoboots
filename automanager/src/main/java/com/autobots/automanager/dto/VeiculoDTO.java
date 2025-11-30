package com.autobots.automanager.dto;

import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.entitades.Venda;
import com.autobots.automanager.enumeracoes.TipoVeiculo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VeiculoDTO {
    private Long id;
    private TipoVeiculo tipoVeiculo;
    private String modelo;
    private String placa;
    private Usuario proprietario;
    private Set<Venda> vendas;
}

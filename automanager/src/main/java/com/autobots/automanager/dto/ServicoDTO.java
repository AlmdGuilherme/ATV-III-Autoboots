package com.autobots.automanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServicoDTO {
    private Long id;
    private String nome;
    private double valor;
    private String descricao;
}

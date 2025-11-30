package com.autobots.automanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MercadoriaDTO {
    private Long id;
    private Date validade;
    private Date fabricacao;
    private Date cadastro;
    private String nome;
    private long quantidade;
    private double valor;
    private String descricao;
}

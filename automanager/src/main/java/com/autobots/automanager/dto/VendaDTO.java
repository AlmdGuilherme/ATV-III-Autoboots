package com.autobots.automanager.dto;

import com.autobots.automanager.entitades.Mercadoria;
import com.autobots.automanager.entitades.Servico;
import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.entitades.Veiculo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendaDTO {
    private Long id;
    private Date cadastro;
    private String identificacao;
    private Usuario cliente;
    private Usuario funcionario;
    private Set<Mercadoria> mercadorias;
    private Set<Servico> servicos;
    private Veiculo veiculo;
}

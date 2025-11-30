package com.autobots.automanager.dto;

import com.autobots.automanager.entitades.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpresaDTO {
    private Long id;
    private String razaoSocial;
    private String nomeFantasia;
    private Set<Telefone> telefones;
    private Endereco endereco;
    private Date dataCadastro;
    private Set<Usuario> usuarios;
    private Set<Mercadoria> mercadorias;
    private Set<Servico> servicos;
    private Set<Venda> vendas;
}
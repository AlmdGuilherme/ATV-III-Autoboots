package com.autobots.automanager.dto;

import com.autobots.automanager.entitades.*;
import com.autobots.automanager.enumeracoes.PerfilUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String nome;
    private String nomeSocial;
    private Set<PerfilUsuario> perfis;
    private Set<Telefone> telefones;
    private Endereco endereco;
    private Set<Documento> documentos;
    private Set<Email> emails;
    private Set<Credencial> credenciais;
    private Set<Mercadoria> mercadorias;
    private Set<Venda> vendas;
    private Set<Veiculo> veiculos;
}

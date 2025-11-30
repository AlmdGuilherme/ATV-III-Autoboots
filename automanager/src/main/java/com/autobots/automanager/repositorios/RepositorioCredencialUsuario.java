package com.autobots.automanager.repositorios;

import com.autobots.automanager.entitades.CredencialUsuarioSenha;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioCredencialUsuario extends JpaRepository<CredencialUsuarioSenha, Long> {}
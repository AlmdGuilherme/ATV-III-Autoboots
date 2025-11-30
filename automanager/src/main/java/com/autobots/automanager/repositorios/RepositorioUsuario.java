package com.autobots.automanager.repositorios;

import com.autobots.automanager.entitades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioUsuario extends JpaRepository<Usuario, Long> {}

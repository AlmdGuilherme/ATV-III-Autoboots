package com.autobots.automanager.repositorios;

import com.autobots.automanager.entitades.Documento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioDocumento extends JpaRepository<Documento, Long> {}

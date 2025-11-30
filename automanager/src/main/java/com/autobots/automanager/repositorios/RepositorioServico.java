package com.autobots.automanager.repositorios;

import com.autobots.automanager.entitades.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioServico extends JpaRepository<Servico, Long> {}

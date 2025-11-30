package com.autobots.automanager.modelos.selecionadores;

import com.autobots.automanager.entitades.Empresa;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmpresaSelecionador {
    public Empresa selecionadorEmpresa(List<Empresa> empresas, long id) {
        Empresa empresaSelecionada = null;
        for (Empresa empresa: empresas) {
            if (empresa.getId() == id) {
                empresaSelecionada = empresa;
            }
        }
        return empresaSelecionada;
    }
}

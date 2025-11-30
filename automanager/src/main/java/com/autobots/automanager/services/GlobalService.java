package com.autobots.automanager.services;

import com.autobots.automanager.entitades.Veiculo;
import com.autobots.automanager.entitades.Venda;
import com.autobots.automanager.repositorios.RepositorioEmpresa;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import com.autobots.automanager.repositorios.RepositorioVeiuculo;
import com.autobots.automanager.repositorios.RepositorioVenda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GlobalService {
    @Autowired
    private RepositorioVenda repositorioVenda;
    @Autowired
    private RepositorioVeiuculo repositorioVeiuculo;
    @Autowired
    private RepositorioEmpresa repositorioEmpresa;

    public String removeUserFromSale(long userId) {
        List<Venda> vendas = repositorioVenda.findAll();
        String response = "Não há nenhuma venda cadastrada";
        if (vendas.isEmpty()) {
            return response;
        }
        for (Venda venda: vendas) {
            if (venda.getFuncionario() != null && venda.getFuncionario().getId() == userId) {
                venda.setFuncionario(null);
                repositorioVenda.save(venda);
            }
            if (venda.getCliente() != null && venda.getCliente().getId() == userId) {
                venda.setCliente(null);
                repositorioVenda.save(venda);
            }
        }
        response = "Usuário desvinculado dos funcionários/cliente(s) com sucesso!";
        return response;
    }

    public String removeUserFromVehicle(long userId) {
        List<Veiculo> veiculos = repositorioVeiuculo.findAll();
        String response = "Não há veículos cadastrados";
        if (veiculos.isEmpty()) {
            return response;
        }
        for (Veiculo veiculo: veiculos) {
            if (veiculo.getProprietario() != null && veiculo.getProprietario().getId() == userId) {
                veiculo.setProprietario(null);
                repositorioVeiuculo.save(veiculo);
            }
        }
        response = "Usuário desvinculados aos proprietários de veículos com sucesso!";
        return response;
    }
}

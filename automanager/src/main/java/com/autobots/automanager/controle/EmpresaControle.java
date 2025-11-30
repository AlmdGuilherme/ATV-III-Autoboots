package com.autobots.automanager.controle;

import com.autobots.automanager.entitades.*;
import com.autobots.automanager.modelos.AdicionadorLinkEmpresa;
import com.autobots.automanager.modelos.selecionadores.EmpresaSelecionador;
import com.autobots.automanager.repositorios.RepositorioEmpresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/empresas")
public class EmpresaControle {
    @Autowired
    private RepositorioEmpresa repositorioEmpresa;
    @Autowired
    private EmpresaSelecionador empresaSelecionador;
    @Autowired
    private AdicionadorLinkEmpresa adicionadorLinkEmpresa;

    @GetMapping
    public ResponseEntity<List<Empresa>> listarEmpresas() {
        List<Empresa> empresas = repositorioEmpresa.findAll();
        if (empresas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLinkEmpresa.adicionadorLinkGeral(empresas);
            return new ResponseEntity<>(empresas, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empresa> findyCompanyById(@PathVariable Long id) {
        List<Empresa> empresas = repositorioEmpresa.findAll();
        Empresa empresa = empresaSelecionador.selecionadorEmpresa(empresas, id);
        if (empresa == null) {
            ResponseEntity<Empresa> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return response;
        } else {
            adicionadorLinkEmpresa.adicionadorLink(empresa);
            ResponseEntity<Empresa> response = new ResponseEntity<>(empresa, HttpStatus.OK);
            return response;
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrarEmpresa(@RequestBody Empresa empresa) {
        empresa.setCadastro(new Date());
        repositorioEmpresa.save(empresa);
        return new ResponseEntity<>("Empresa cadastrada com sucesso!", HttpStatus.OK);
    }

    @PutMapping("/adicionarTelefones/{companyId}")
    public ResponseEntity<String> adicionarTelefone(@PathVariable long companyId, @RequestBody List<Telefone> telefones) {
        return repositorioEmpresa.findById(companyId)
                .map(empresaSelecionada -> {
                    for (Telefone telefone : telefones) {
                        empresaSelecionada.getTelefones().add(telefone);
                    }
                    repositorioEmpresa.save(empresaSelecionada);
                    return new ResponseEntity<>("Telefones adicionados a empresa: " + empresaSelecionada.getNomeFantasia() + " com sucesso!", HttpStatus.OK);
                })
                .orElseGet(() -> {
                    return new ResponseEntity<>("Não foi possível encontrar a empresa com o ID: " + companyId, HttpStatus.NOT_FOUND);
                });
    }

    @PutMapping("/adicionarUsuarios/{companyId}")
    public ResponseEntity<String> adicionarUsuarios(@PathVariable long companyId, @RequestBody List<Usuario> usuarios) {
       return repositorioEmpresa.findById(companyId)
               .map(empresaSelecionada -> {
                   for (Usuario usuario : usuarios) {
                       empresaSelecionada.getUsuarios().add(usuario);
                   }
                   repositorioEmpresa.save(empresaSelecionada);
                   return new ResponseEntity<>("Usuários adicionados a empresa: " + empresaSelecionada.getNomeFantasia() + " com sucesso!", HttpStatus.OK);
               })
               .orElseGet(() -> {
                   return new ResponseEntity<>("Não foi possível encontrar a empresa com o ID: " + companyId, HttpStatus.NOT_FOUND);
               });
    }

    @PutMapping("/adicionarServicos/{companyId}")
    public ResponseEntity<String> adicionarServicos(@PathVariable long companyId, @RequestBody List<Servico> servicos) {
        return repositorioEmpresa.findById(companyId)
                .map(empresaSelecionada -> {
                    for (Servico servico: servicos) {
                        empresaSelecionada.getServicos().add(servico);
                    }
                    repositorioEmpresa.save(empresaSelecionada);
                    return new ResponseEntity<>("Serviços adicionados a empresa: " + empresaSelecionada.getNomeFantasia() + " com sucesso!", HttpStatus.OK);
                })
                .orElseGet(() -> {
                    return new ResponseEntity<>("Não foi possível encontrar a empresa com o ID: " + companyId, HttpStatus.NOT_FOUND);
                });
    }

    @PutMapping("/adicionarMercadorias/{companyId}")
    public ResponseEntity<String> adicionarMercadorias(@PathVariable long companyId, @RequestBody List<Mercadoria> mercadorias) {
        return repositorioEmpresa.findById(companyId)
                .map(empresaSelecionada -> {
                    for (Mercadoria mercadoria: mercadorias) {
                        empresaSelecionada.getMercadorias().add(mercadoria);
                    }
                    repositorioEmpresa.save(empresaSelecionada);
                    return new ResponseEntity<>("Mercadorias adicionadas a empresa: " + empresaSelecionada.getNomeFantasia() + " com sucesso!", HttpStatus.OK);
                })
                .orElseGet(() -> {
                    return new ResponseEntity<>("Não foi possível encontrar a empresa com o ID: " + companyId, HttpStatus.NOT_FOUND);
                });
    }

    @PutMapping("/atualizar")
    public ResponseEntity<String> atualizarEmpresa(@RequestBody Empresa empresa) {
        Empresa empresaBanco = repositorioEmpresa.findById(empresa.getId()).get();
        if (empresa != null) {
            if (empresaBanco != null) {
                empresaBanco.setRazaoSocial(empresa.getRazaoSocial());
                empresaBanco.setNomeFantasia(empresa.getNomeFantasia());
                empresaBanco.setTelefones(empresa.getTelefones());
                empresaBanco.setEndereco(empresa.getEndereco());
                empresaBanco.setUsuarios(empresa.getUsuarios());
                empresaBanco.setMercadorias(empresa.getMercadorias());
                empresaBanco.setServicos(empresa.getServicos());
                empresaBanco.setVendas(empresa.getVendas());
                repositorioEmpresa.save(empresaBanco);
                return new ResponseEntity<>("Empresa atualizada com sucesso", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Documento não encontrado!", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("É preciso ter uma empresa para atualizar", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<String> excluirEmpresa(@PathVariable Long id) {
        if (repositorioEmpresa.findById(id).isPresent()) {
            repositorioEmpresa.deleteById(id);
            return new ResponseEntity<>("Empresa excluida com sucesso!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Empresa não encontrada!", HttpStatus.NOT_FOUND);
        }
    }
}
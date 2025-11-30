package com.autobots.automanager.controle;

import com.autobots.automanager.entitades.Servico;
import com.autobots.automanager.modelos.AdicionadorLinkServico;
import com.autobots.automanager.modelos.selecionadores.ServicoSelecionador;
import com.autobots.automanager.repositorios.RepositorioServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServicoControle {
    @Autowired
    private RepositorioServico repositorioServico;
    @Autowired
    private ServicoSelecionador servicoSelecionador;
    @Autowired
    private AdicionadorLinkServico adicionadorLinkServico;

    @GetMapping
    public ResponseEntity<List<Servico>> listarServicos() {
        List<Servico> servicos = repositorioServico.findAll();
        if (servicos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLinkServico.adicionadorLinkGeral(servicos);
            return new ResponseEntity<>(servicos, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servico> buscarServicoPorId(@PathVariable Long id) {
        List<Servico> servicos = repositorioServico.findAll();
        Servico servicoSelecionado = servicoSelecionador.selecionadorServico(servicos, id);
        if (servicoSelecionado == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLinkServico.adicionadorLink(servicoSelecionado);
            return new ResponseEntity<>(servicoSelecionado, HttpStatus.OK);
        }
    }

    @PutMapping("/cadastrar")
    public ResponseEntity<String> cadastrarServico(@RequestBody Servico servico) {
        if (servico == null) {
            return new ResponseEntity<>("É preciso ter um serviço para cadastrar!",HttpStatus.BAD_REQUEST);
        } else {
            repositorioServico.save(servico);
            return new ResponseEntity<>("Serviço cadastrado com sucesso!", HttpStatus.CREATED);
        }
    }

    @PostMapping("/atualizar/{id}")
    public ResponseEntity<String> atualizarServico(@PathVariable Long id, @RequestBody Servico servico) {
        Servico servicoBanco = repositorioServico.findById(id).get();
        if (servico != null) {
            if (servicoBanco != null) {
                servicoBanco.setNome(servico.getNome());
                servicoBanco.setValor(servico.getValor());
                servicoBanco.setDescricao(servico.getDescricao());
                repositorioServico.save(servicoBanco);
                return new ResponseEntity<>("Serviço atualizado com sucesso!", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Não foi possível encontrar este serviço...", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("É preciso ter um serviço para atualizar!", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<String> excluirServico(@PathVariable Long id) {
        Servico servicoBanco = repositorioServico.findById(id).get();
        if (servicoBanco != null) {
            repositorioServico.delete(servicoBanco);
            return new ResponseEntity<>("Serviço deletado com sucesso!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Não foi possível econtrar este endereço!", HttpStatus.NOT_FOUND);
        }
    }
}

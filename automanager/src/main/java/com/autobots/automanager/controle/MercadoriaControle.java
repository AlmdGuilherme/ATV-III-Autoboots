package com.autobots.automanager.controle;

import com.autobots.automanager.entitades.Mercadoria;
import com.autobots.automanager.modelos.AdicionadorLinkMercadoria;
import com.autobots.automanager.modelos.selecionadores.MercadoriaSelecionador;
import com.autobots.automanager.repositorios.RepositorioMercadoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/mercadorias")
public class MercadoriaControle {
    @Autowired
    private RepositorioMercadoria repositorioMercadoria;
    @Autowired
    private MercadoriaSelecionador mercadoriaSelecionador;
    @Autowired
    private AdicionadorLinkMercadoria adicionadorLinkMercadoria;

    @GetMapping
    public ResponseEntity<List<Mercadoria>> listarMercadoria() {
        List<Mercadoria> mercadorias = repositorioMercadoria.findAll();
        if (mercadorias.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            adicionadorLinkMercadoria.adicionadorLinkGeral(mercadorias);
            return new ResponseEntity<>(mercadorias, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mercadoria> buscarMercadoriaPorId(@PathVariable long id) {
        List<Mercadoria> mercadorias = repositorioMercadoria.findAll();
        Mercadoria mercadoria = mercadoriaSelecionador.selecionadorMercadoria(mercadorias, id);
        if (mercadoria == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLinkMercadoria.adicionadorLink(mercadoria);
            return new ResponseEntity<>(mercadoria, HttpStatus.OK);
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrarMercadoria(@RequestBody Mercadoria mercadoria) {
        mercadoria.setCadastro(new Date());
        repositorioMercadoria.save(mercadoria);
        return new ResponseEntity<>("Mercadoria cadastrada com sucesso", HttpStatus.CREATED);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<String> atualizarMercadoria(@PathVariable long id, @RequestBody Mercadoria mercadoria) {
        Mercadoria mercadoriaBanco = repositorioMercadoria.findById(id).get();
        if (mercadoria != null) {
            if (mercadoriaBanco != null) {
                mercadoriaBanco.setValidade(mercadoria.getValidade());
                mercadoriaBanco.setFabricao(mercadoria.getFabricao());
                mercadoriaBanco.setCadastro(mercadoria.getCadastro());
                mercadoriaBanco.setNome(mercadoria.getNome());
                mercadoriaBanco.setQuantidade(mercadoria.getQuantidade());
                mercadoriaBanco.setValor(mercadoria.getValor());
                mercadoriaBanco.setDescricao(mercadoria.getDescricao());
                repositorioMercadoria.save(mercadoriaBanco);
                return new ResponseEntity<>("Mercadoria atualizada com sucesso!", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Não foi possível encontrar a mercadoria!", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("Não é possível passar valores nulos para atualizar uma mercadoria!", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<String> excluirMercadoria(@PathVariable long id) {
        if (repositorioMercadoria.findById(id).isPresent()) {
            repositorioMercadoria.deleteById(id);
            return new ResponseEntity<>("Mercadoria excluida com sucesso!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Mercadoria não encontrada!", HttpStatus.NOT_FOUND);
        }
    }
}
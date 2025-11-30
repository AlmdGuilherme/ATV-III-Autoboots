package com.autobots.automanager.controle;

import com.autobots.automanager.entitades.Telefone;
import com.autobots.automanager.modelos.AdicionadorLinkTelefone;
import com.autobots.automanager.modelos.selecionadores.TelefoneSelecionador;
import com.autobots.automanager.repositorios.RepositorioTelefone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/telefones")
public class TelefoneControle {
    @Autowired
    private RepositorioTelefone repositorioTelefone;
    @Autowired
    private TelefoneSelecionador telefoneSelecionador;
    @Autowired
    private AdicionadorLinkTelefone adicionadorLinkTelefone;


    @GetMapping
    public ResponseEntity<List<Telefone>> listarTelefones() {
        List<Telefone> telefones = repositorioTelefone.findAll();
        if (telefones.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLinkTelefone.adicionadorLinkGeral(telefones);
            return new ResponseEntity<>(telefones, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Telefone> buscarTelefonePorId(@PathVariable long id) {
        List<Telefone> telefones = repositorioTelefone.findAll();
        Telefone telefoneSelecionado = telefoneSelecionador.selecionadorTelefone(telefones, id);
        if (telefoneSelecionado == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLinkTelefone.adicionadorLink(telefoneSelecionado);
            return new ResponseEntity<>(telefoneSelecionado, HttpStatus.OK);
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrarTelefone(@RequestBody Telefone telefone) {
        if (telefone != null) {
            repositorioTelefone.save(telefone);
            return new ResponseEntity<>("Telefone cadastrado com sucesso!", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Não foi possível realizar o cadastro!", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<String> editarTelefone(@PathVariable long id, @RequestBody Telefone telefone) {
        Telefone telefoneBanco = repositorioTelefone.findById(id).get();
        if (telefone != null) {
            if (telefoneBanco != null) {
                telefoneBanco.setDdd(telefone.getDdd());
                telefoneBanco.setNumero(telefone.getNumero());
                repositorioTelefone.save(telefoneBanco);
                return new ResponseEntity<>("Telefone atualizado com sucesso!", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Nenhum telefone encontrado no banco!", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("Não foi possível atualizar o telefone!", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<String> excluirTelefone(@PathVariable long id) {
        Telefone telefoneBanco = repositorioTelefone.findById(id).get();
        if (telefoneBanco != null) {
            repositorioTelefone.delete(telefoneBanco);
            return new ResponseEntity<>("Telefone deletado com sucesso!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Não foi possível encontrar o telefone", HttpStatus.NOT_FOUND);
        }
    }
}
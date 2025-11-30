package com.autobots.automanager.controle;

import com.autobots.automanager.entitades.Endereco;
import com.autobots.automanager.modelos.AdicionadorLinkEnderecos;
import com.autobots.automanager.modelos.selecionadores.EnderecoSelecionador;
import com.autobots.automanager.repositorios.RepositorioEndereco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enderecos")
public class EnderecoControle {
    @Autowired
    private RepositorioEndereco  repositorioEndereco;
    @Autowired
    private EnderecoSelecionador selecionadorEndereco;
    @Autowired
    private AdicionadorLinkEnderecos adicionadorLinkEnderecos;

    @GetMapping
    public ResponseEntity<List<Endereco>> listarEnderecos() {
        List<Endereco> enderecos = repositorioEndereco.findAll();
        if (enderecos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLinkEnderecos.adicionadorLinkGeral(enderecos);
            return new ResponseEntity<>(enderecos, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Endereco> findAddressById(@PathVariable long id) {
        List<Endereco> enderecos = repositorioEndereco.findAll();
        Endereco enderecoSelecionado = selecionadorEndereco.selecionadorEndereco(enderecos, id);
        if (enderecoSelecionado == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLinkEnderecos.adicionadorLink(enderecoSelecionado);
            return new ResponseEntity<>(enderecoSelecionado, HttpStatus.OK);
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Endereco> cadastrarEndereco(@RequestBody Endereco endereco) {
        repositorioEndereco.save(endereco);
        return new ResponseEntity<>(endereco, HttpStatus.CREATED);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<String> atualizarEndereco(@PathVariable long id, @RequestBody Endereco enderecoAtualizar) {
        Endereco enderecoBanco = repositorioEndereco.findById(id).get();
        if (enderecoAtualizar != null) {
            if (enderecoBanco!= null) {
                enderecoBanco.setBairro(enderecoAtualizar.getBairro());
                enderecoBanco.setRua(enderecoAtualizar.getRua());
                enderecoBanco.setCidade(enderecoAtualizar.getCidade());
                enderecoBanco.setEstado(enderecoAtualizar.getEstado());
                enderecoBanco.setNumero(enderecoAtualizar.getNumero());
                enderecoBanco.setCodigoPostal(enderecoAtualizar.getCodigoPostal());
                enderecoBanco.setInformacoesAdicionais(enderecoAtualizar.getInformacoesAdicionais());
                repositorioEndereco.save(enderecoBanco);
                return new ResponseEntity<>("Endereço atualizado com sucesso", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Não foi possível encontrar este endereço...", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("É preciso ter um endereço para atualizar!", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<String> excluirDocumento(@PathVariable long id) {
        Endereco enderecoBanco = repositorioEndereco.findById(id).get();
        if  (enderecoBanco != null) {
            repositorioEndereco.delete(enderecoBanco);
            return new ResponseEntity<>("Endereço excluido com sucesso!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Não foi possível encontrar este endereço!", HttpStatus.NOT_FOUND);
        }
    }

}

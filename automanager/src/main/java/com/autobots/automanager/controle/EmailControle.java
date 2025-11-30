package com.autobots.automanager.controle;

import com.autobots.automanager.entitades.Email;
import com.autobots.automanager.modelos.AdicionadorLinkEmail;
import com.autobots.automanager.modelos.selecionadores.EmailSelecionador;
import com.autobots.automanager.repositorios.RepositorioEmail;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emails")
public class EmailControle {
    @Autowired
    private RepositorioEmail  repositorioEmail;
    @Autowired
    private RepositorioUsuario repositorioUsuario;
    @Autowired
    private EmailSelecionador selecionadorEmail;
    @Autowired
    private AdicionadorLinkEmail adicionadorLinkEmail;

    @GetMapping
    public ResponseEntity<List<Email>> listarEmails(){
        List<Email> emails = repositorioEmail.findAll();
        if (emails.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLinkEmail.adicionadorLinkGeral(emails);
            return new ResponseEntity<>(emails, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Email> findEmailById(@PathVariable Long id){
        List<Email> emails = repositorioEmail.findAll();
        Email email = selecionadorEmail.selecionadorEmail(emails, id);
        if (email == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLinkEmail.adicionadorLink(email);
            return new ResponseEntity<>(email, HttpStatus.OK);
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Email> cadastrarEmail(@RequestBody Email email){
        repositorioEmail.save(email);
        return new ResponseEntity<>(email, HttpStatus.CREATED);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<String> atualizarEmail(@RequestBody Email emailAtualizado){
        Email emailBanco = repositorioEmail.findById(emailAtualizado.getId()).get();
        List<Email>  emails = repositorioEmail.findAll();
        boolean condicao = false;
        for (Email email : emails){
            if (email.getEndereco() == emailAtualizado.getEndereco()){
                condicao = true;
            }
        }
        if (!condicao){
            if (emailAtualizado != null) {
                if (emailBanco != null) {
                    emailBanco.setEndereco(emailAtualizado.getEndereco());
                    repositorioEmail.save(emailBanco);
                    return new ResponseEntity<>("Email atualizado com sucesso", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Email não encontrado", HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity<>("É preciso ter um email para atualizar", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Não foi possível atualizar o email - endereço de email já existente", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<String> deletarEmail(@PathVariable long id) {
        if (repositorioEmail.existsById(id)){
            repositorioEmail.deleteById(id);
            return new ResponseEntity<>("Email deletado com sucesso", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Email não encontrado", HttpStatus.NOT_FOUND);
        }
    }
}
package com.autobots.automanager.controle;

import com.autobots.automanager.entitades.CredencialCodigoBarra;
import com.autobots.automanager.entitades.CredencialUsuarioSenha;
import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.modelos.AdicionadorLinkCredenciaisUsuario;
import com.autobots.automanager.modelos.AdicionadorLinkCredencialCodigoBarras;
import com.autobots.automanager.repositorios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/credenciais")
public class CredencialControle {
    @Autowired
    private RepositorioEmpresa repositorioEmpresa;
    @Autowired
    private RepositorioUsuario repositorioUsuario;
    @Autowired
    private RepositorioVenda repositorioVenda;
    @Autowired
    private RepositorioServico repositorioServico;
    @Autowired
    private RepositorioMercadoria repositorioMercadoria;
    @Autowired
    private RepositorioCredencialUsuario repositorioCredencialUsuario;
    @Autowired
    private AdicionadorLinkCredenciaisUsuario adicionadorLinkCredenciaisUsuario;
    @Autowired
    private RepositorioCredencialCodigoBarra repositorioCredencialCodigoBarra;
    @Autowired
    private AdicionadorLinkCredencialCodigoBarras adicionadorLinkCredencialCodigoBarras;


    @GetMapping("/credenciais-usuarios")
    public ResponseEntity<List<CredencialUsuarioSenha>> buscarCredenciaisUsuarios() {
        List<CredencialUsuarioSenha> credenciais = repositorioCredencialUsuario.findAll();
        if (credenciais.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLinkCredenciaisUsuario.adicionadorLinkGeral(credenciais);
            return new ResponseEntity<>(credenciais, HttpStatus.OK);
        }
    }

    @GetMapping("/credenciais-usuarios/{id}")
    public ResponseEntity<CredencialUsuarioSenha> buscarCredenciaisUsuariosId(@PathVariable Long id) {
        CredencialUsuarioSenha credencial = repositorioCredencialUsuario.findById(id).orElse(null);
        if (credencial == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLinkCredenciaisUsuario.adicionadorLink(credencial);
            return new ResponseEntity<>(credencial, HttpStatus.OK);
        }
    }

    @PostMapping("/cadastrar-credencial/{idUsuario}")
    public ResponseEntity<?> cadastrarCredencialUsuario(@PathVariable long idUsuario, @RequestBody CredencialUsuarioSenha credencial) {
        Usuario usuario = repositorioUsuario.findById(idUsuario).orElse(null);
        if (usuario == null) {
            return new ResponseEntity<>("Usuário não encontrado.", HttpStatus.NOT_FOUND);
        }
        List<CredencialUsuarioSenha> credenciaisBanco = repositorioCredencialUsuario.findAll();
        boolean alreadyExists = false;
        for (CredencialUsuarioSenha c : credenciaisBanco) {
            if (c.getNomeUsuario().equals(credencial.getNomeUsuario())) {
                alreadyExists = true;
                break;
            }
        }
        if (alreadyExists) {
            return new ResponseEntity<>("Credenciais existentes!", HttpStatus.CONFLICT);
        } else {
            credencial.setCriacao(new Date());
            usuario.getCredenciais().add(credencial);
            repositorioUsuario.save(usuario);
            return new ResponseEntity<>("Credenciais cadastradas com sucesso!", HttpStatus.CREATED);
        }
    }

    @PutMapping("/atualizar-credencial/{idCredencial}")
    public ResponseEntity<?> atualizarCredencialUsuario(@PathVariable long idCredencial, @RequestBody CredencialUsuarioSenha credencialAtualizada) {
        CredencialUsuarioSenha credencialExistente = repositorioCredencialUsuario.findById(idCredencial).get();
        if (credencialExistente == null) {
            return new ResponseEntity<>("Credencial não econtrada!", HttpStatus.NOT_FOUND);
        } else {
            if (credencialAtualizada != null) {
                if (credencialAtualizada.getNomeUsuario() != null && credencialAtualizada.getSenha() != null) {
                    credencialExistente.setNomeUsuario(credencialAtualizada.getNomeUsuario());
                    credencialExistente.setSenha(credencialAtualizada.getSenha());
                    repositorioCredencialUsuario.save(credencialExistente);
                    return new ResponseEntity<>("Credencial atualizada com sucesso!", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Dados inválidos para atualização!", HttpStatus.BAD_REQUEST);
                }
            }
            return new ResponseEntity<>("Nenhum dado para atualizar!", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deletar-credencial/{idCredencial}")
    public ResponseEntity<?> deletarCredencialUsuario(@PathVariable long idCredencial) {
        CredencialUsuarioSenha credencialExistente = repositorioCredencialUsuario.findById(idCredencial).get();
        if (credencialExistente == null) {
            return new ResponseEntity<>("Credencial não encontrada!", HttpStatus.NOT_FOUND);
        } else {
            repositorioCredencialUsuario.delete(credencialExistente);
            return new ResponseEntity<>("Credencial deletada com sucesso!", HttpStatus.OK);
        }
    }

    // Controle - código barras
    @GetMapping("/credenciais-codigo-barras")
    public ResponseEntity<List<CredencialCodigoBarra>> buscarCredenciaisCodigoBarra() {
        List<CredencialCodigoBarra> credencialCodigoBarras = repositorioCredencialCodigoBarra.findAll();
        if (credencialCodigoBarras.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLinkCredencialCodigoBarras.adicionadorLinkGeral(credencialCodigoBarras);
            return new ResponseEntity<>(credencialCodigoBarras, HttpStatus.OK);
        }
    }

    @GetMapping("/credenciais-codigo-barras/{id}")
    public ResponseEntity<CredencialCodigoBarra> buscarCredenciaisCodigoBarraId(@PathVariable Long id) {
        CredencialCodigoBarra credencial = repositorioCredencialCodigoBarra.findById(id).get();
        if (credencial == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLinkCredencialCodigoBarras.adicionadorLink(credencial);
            return new ResponseEntity<>(credencial, HttpStatus.OK);
        }
    }

    @PostMapping("/cadastrar-credencial-codigo/{idUsuario}")
    public ResponseEntity<?> cadastrarCredencialCodigoBarra(@PathVariable long idUsuario, @RequestBody CredencialCodigoBarra credencial) {
        Usuario usuario = repositorioUsuario.findById(idUsuario).orElse(null);
        if (usuario == null) {
            return new ResponseEntity<>("Usuário não encontrado.", HttpStatus.NOT_FOUND);
        }
        List<CredencialCodigoBarra> credenciaisBanco = repositorioCredencialCodigoBarra.findAll();
        boolean alreadyExists = false;
        for (CredencialCodigoBarra c : credenciaisBanco) {
            if (c.getCodigo() == credencial.getCodigo()) {
                alreadyExists = true;
                break;
            }
        }
        if (alreadyExists) {
            return new ResponseEntity<>("Credenciais existentes!", HttpStatus.CONFLICT);
        } else {
            credencial.setCriacao(new Date());
            usuario.getCredenciais().add(credencial);
            repositorioUsuario.save(usuario);
            return new ResponseEntity<>("Credenciais cadastradas com sucesso!", HttpStatus.CREATED);
        }
    }

    @PutMapping("/atualizar-credencial-codigo/{idCredencial}")
    public ResponseEntity<?> atualizarCredencialCodigoBarra(@PathVariable long idCredencial, @RequestBody CredencialCodigoBarra credencialAtualizada) {
        CredencialCodigoBarra credencialExistente = repositorioCredencialCodigoBarra.findById(idCredencial).get();
        if (credencialExistente == null) {
            return new ResponseEntity<>("Credencial não econtrada!", HttpStatus.NOT_FOUND);
        } else {
            if (credencialAtualizada != null) {
                if (credencialAtualizada.getCodigo() != 0) {
                    credencialExistente.setCodigo(credencialAtualizada.getCodigo());
                    repositorioCredencialCodigoBarra.save(credencialExistente);
                    return new ResponseEntity<>("Credencial atualizada com sucesso!", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Dados inválidos para atualização!", HttpStatus.BAD_REQUEST);
                }
            }
            return new ResponseEntity<>("Nenhum dado para atualizar!", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deletar-credencial-codigo/{idCredencial}")
    public ResponseEntity<?> deletarCredencialCodigoBarra(@PathVariable long idCredencial) {
        CredencialCodigoBarra credencialExistente = repositorioCredencialCodigoBarra.findById(idCredencial).get();
        if (credencialExistente == null) {
            return new ResponseEntity<>("Credencial não encontrada!", HttpStatus.NOT_FOUND);
        } else {
            repositorioCredencialCodigoBarra.delete(credencialExistente);
            return new ResponseEntity<>("Credencial deletada com sucesso!", HttpStatus.OK);
        }
    }
}
package com.autobots.automanager.controle;

import com.autobots.automanager.entitades.Email;
import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.autobots.automanager.modelos.AdicionadorLinkUsuario;
import com.autobots.automanager.modelos.selecionadores.UsuarioSelecionador;
import com.autobots.automanager.repositorios.RepositorioEmail;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import com.autobots.automanager.services.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@RequestMapping("/usuarios")
public class UsuarioControle {
    @Autowired
    private RepositorioUsuario repositorioUsuario;
    @Autowired
    private UsuarioSelecionador selecionadorUsuario;
    @Autowired
    private AdicionadorLinkUsuario adicionadorLinkUsuario;
    @Autowired
    private GlobalService service;

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = repositorioUsuario.findAll();
        if (usuarios.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLinkUsuario.adicionadorLinkGeral(usuarios);
            return new ResponseEntity<>(usuarios, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable long id) {
        List<Usuario> usuarios = repositorioUsuario.findAll();
        Usuario usuario = selecionadorUsuario.selecionadorUsuario(usuarios, id);
        if (usuario == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLinkUsuario.adicionadorLink(usuario);
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        }
    }

    @PostMapping("/cadastrar-cliente")
    public ResponseEntity<Usuario> cadastrarCliente(@RequestBody Usuario usuario) {
        if (usuario == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            usuario.getPerfis().add(PerfilUsuario.CLIENTE);
            repositorioUsuario.save(usuario);
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        }
    }

    @PostMapping("/cadastrar-fornecedor")
    public ResponseEntity<Usuario> cadastrarFornecedor(@RequestBody Usuario usuario) {
        if (usuario == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            usuario.getPerfis().add(PerfilUsuario.FORNECEDOR);
            repositorioUsuario.save(usuario);
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        }
    }

    @PostMapping("/cadastrar-funcionario")
    public ResponseEntity<Usuario> cadastrarFuncionario(@RequestBody Usuario usuario) {
        if (usuario == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            usuario.getPerfis().add(PerfilUsuario.FUNCIONARIO);
            repositorioUsuario.save(usuario);
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable long id, @RequestBody Usuario usuario) {
        if (usuario == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            List<Usuario> usuarios = repositorioUsuario.findAll();
            Usuario usuarioSelecionado = selecionadorUsuario.selecionadorUsuario(usuarios, id);
            if (usuarioSelecionado == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                usuarioSelecionado.setNome(usuario.getNome());
                usuarioSelecionado.setNomeSocial(usuario.getNomeSocial());
                repositorioUsuario.save(usuarioSelecionado);
                return new ResponseEntity<>(usuarioSelecionado, HttpStatus.OK);
            }

        }
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<String> deletarUsuario(@PathVariable long id) {
        List<Usuario> usuarios = repositorioUsuario.findAll();
        Usuario usuarioSelecionado = selecionadorUsuario.selecionadorUsuario(usuarios, id);
        if (usuarioSelecionado == null) {
            return new ResponseEntity<>("Usuário não econtrado", HttpStatus.NOT_FOUND);
        } else {
            service.removeUserFromSale(usuarioSelecionado.getId());
            service.removeUserFromVehicle(usuarioSelecionado.getId());
            repositorioUsuario.delete(usuarioSelecionado);
            return new ResponseEntity<>("Usuário deletado com sucesso!", HttpStatus.OK);
        }
    }
}
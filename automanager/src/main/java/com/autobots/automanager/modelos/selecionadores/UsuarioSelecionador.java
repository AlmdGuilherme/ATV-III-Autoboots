package com.autobots.automanager.modelos.selecionadores;

import com.autobots.automanager.entitades.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioSelecionador {
    public Usuario selecionadorUsuario(List<Usuario> usuarios, long id) {
        Usuario usuarioSelecionado = null;
        for (Usuario usuario: usuarios) {
            if (usuario.getId() == id) {
                usuarioSelecionado = usuario;
            }
        }
        return usuarioSelecionado;
    }
}

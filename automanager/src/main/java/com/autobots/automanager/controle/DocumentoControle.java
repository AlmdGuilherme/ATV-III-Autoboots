package com.autobots.automanager.controle;

import com.autobots.automanager.entitades.Documento;
import com.autobots.automanager.modelos.AdicionadorLinkDocumento;
import com.autobots.automanager.modelos.selecionadores.DocumentoSelecionador;
import com.autobots.automanager.repositorios.RepositorioDocumento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/documentos")
public class DocumentoControle {
    @Autowired
    private RepositorioDocumento repositorioDocumento;
    @Autowired
    private AdicionadorLinkDocumento adicionadorLinkDocumento;
    @Autowired
    private DocumentoSelecionador documentoSelecionador;

    @GetMapping
    public ResponseEntity<List<Documento>> listarDocumentos(){
        List<Documento> documentos = repositorioDocumento.findAll();
        if (documentos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLinkDocumento.adicionadorLinkGeral(documentos);
            return new ResponseEntity<>(documentos, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Documento> findDocById(@PathVariable long id){
        List<Documento> documentos = repositorioDocumento.findAll();
        Documento documento = documentoSelecionador.selecionadorDocumento(documentos, id);
        if (documento == null) {
            ResponseEntity<Documento> response = new ResponseEntity(HttpStatus.NOT_FOUND);
            return response;
        } else {
            adicionadorLinkDocumento.adicionadorLink(documento);
            ResponseEntity<Documento> response = new ResponseEntity<>(documento, HttpStatus.OK);
            return response;
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrar(@RequestBody Documento documento){
        repositorioDocumento.save(documento);
        return new ResponseEntity<>("Documento cadastrado com sucesso!", HttpStatus.OK);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<String> atualizar(@RequestBody Documento documento){
        Documento documentoBanco = repositorioDocumento.findById(documento.getId()).get();
        if (documento != null) {
            if (documentoBanco != null) {
                documentoBanco.setTipo(documento.getTipo());
                documentoBanco.setDataEmissao(documento.getDataEmissao());
                documentoBanco.setNumero(documento.getNumero());
                repositorioDocumento.save(documentoBanco);
                return new ResponseEntity<>("Documento atualizado com sucesso!", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Documento não encontrado",HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("É preciso ter um documento para atualizar", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<String> excluir(@PathVariable Long id){
        if (repositorioDocumento.findById(id).isPresent()) {
            repositorioDocumento.deleteById(id);
            return new ResponseEntity<>("Documento excluido com sucesso!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Documento não encontrado!", HttpStatus.NOT_FOUND);
        }
    }
}

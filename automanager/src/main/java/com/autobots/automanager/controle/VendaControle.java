package com.autobots.automanager.controle;

import com.autobots.automanager.entitades.Venda;
import com.autobots.automanager.modelos.AdicionadorLinkVenda;
import com.autobots.automanager.modelos.selecionadores.VendaSelecionador;
import com.autobots.automanager.repositorios.RepositorioVenda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/vendas")
public class VendaControle {
    @Autowired
    private RepositorioVenda repositorioVenda;
    @Autowired
    private VendaSelecionador vendaSelecionador;
    @Autowired
    private AdicionadorLinkVenda adicionadorLinkVenda;

    @GetMapping
    public ResponseEntity<List<Venda>> listarVendas(){
        List<Venda> vendas = repositorioVenda.findAll();
        if (vendas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLinkVenda.adicionadorLinkGeral(vendas);
            return new ResponseEntity<>(vendas, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venda> buscarVendaPorId(@PathVariable long id){
        List<Venda> vendas = repositorioVenda.findAll();
        Venda vendaSelecionada = vendaSelecionador.selecionadorVenda(vendas, id);
        if (vendaSelecionada == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLinkVenda.adicionadorLink(vendaSelecionada);
            return new ResponseEntity<>(vendaSelecionada, HttpStatus.OK);
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Venda> cadastrarVenda(@RequestBody Venda venda){
        if (venda != null) {
            venda.setCadastro(new Date());
            repositorioVenda.save(venda);
            return new ResponseEntity<>(venda, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Venda> atualizarVenda(@PathVariable long id, @RequestBody Venda venda) {
        Venda vendaBanco = repositorioVenda.findById(id).get();
        if (venda != null) {
            if (vendaBanco != null) {
                vendaBanco.setIdentificacao(venda.getIdentificacao());
                vendaBanco.setCliente(venda.getCliente());
                vendaBanco.setFuncionario(venda.getFuncionario());
                vendaBanco.setMercadorias(venda.getMercadorias());
                vendaBanco.setServicos(venda.getServicos());
                vendaBanco.setVeiculo(venda.getVeiculo());
                repositorioVenda.save(vendaBanco);
                return new ResponseEntity<>(vendaBanco, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<String> excluirVenda(@PathVariable long id){
        if(repositorioVenda.findById(id).isPresent()){
            repositorioVenda.deleteById(id);
            return new ResponseEntity<>("Venda deletada com sucesso!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Venda não encontrada!", HttpStatus.NOT_FOUND);
        }
    }
}

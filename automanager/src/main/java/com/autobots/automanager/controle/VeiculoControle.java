package com.autobots.automanager.controle;

import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.entitades.Veiculo;
import com.autobots.automanager.entitades.Venda;
import com.autobots.automanager.modelos.AdicionadorLinkVeiculo;
import com.autobots.automanager.modelos.selecionadores.VeiculoSelecionador;
import com.autobots.automanager.repositorios.RepositorioVeiuculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/veiculos")
public class VeiculoControle {
    @Autowired
    private RepositorioVeiuculo repositorioVeiuculo;
    @Autowired
    private VeiculoSelecionador selecionadorSelecionador;
    @Autowired
    private AdicionadorLinkVeiculo adicionadorLinkVeiculo;


    @GetMapping
    public ResponseEntity<List<Veiculo>> listarVeiculos() {
        List<Veiculo> veiculos = repositorioVeiuculo.findAll();
        if (veiculos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLinkVeiculo.adicionadorLinkGeral(veiculos);
            return new ResponseEntity<>(veiculos, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Veiculo> buscarVeiculoPorId(@PathVariable long id) {
        List<Veiculo> veiculos = repositorioVeiuculo.findAll();
        Veiculo veiculo = selecionadorSelecionador.selecionadorVeiculo(veiculos, id);
        if (veiculo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLinkVeiculo.adicionadorLink(veiculo);
            return new ResponseEntity<>(veiculo, HttpStatus.OK);
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Veiculo> cadastrarVeiculo(@RequestBody Veiculo veiculo) {
        if (veiculo == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            repositorioVeiuculo.save(veiculo);
            return new ResponseEntity<>(veiculo, HttpStatus.CREATED);
        }

    }

    @PutMapping("/atualizarProprietario/{id}")
    public ResponseEntity<Veiculo> atualizarProprietarioVeiculo(@PathVariable long id, @RequestBody Usuario novoDono) {
        List<Veiculo> veiculos = repositorioVeiuculo.findAll();
        Veiculo veiculoSelecionado = selecionadorSelecionador.selecionadorVeiculo(veiculos, id);
        if (veiculoSelecionado != null) {
            veiculoSelecionado.setProprietario(novoDono);
            return new ResponseEntity<>(veiculoSelecionado, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/atualizarVendas/{id}")
    public ResponseEntity<Veiculo> atualizarVendaVeiculo(@PathVariable long id, @RequestBody List<Venda> vendas) {
        return repositorioVeiuculo.findById(id)
                .map(veiculoSelecionado -> {
                    for (Venda venda: vendas) {
                        veiculoSelecionado.getVendas().add(venda);
                    }
                    repositorioVeiuculo.save(veiculoSelecionado);
                    return new ResponseEntity<>(veiculoSelecionado, HttpStatus.OK);
                })
                .orElseGet(() -> {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                });
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<String> atualizarVeiculo(@PathVariable long id, @RequestBody Veiculo veiculo) {
        Veiculo veiculoBanco = repositorioVeiuculo.findById(id).get();
        if (veiculoBanco == null) {
            return new ResponseEntity<>("Veículo não encontrado", HttpStatus.NOT_FOUND);
        } else {
            if (veiculo != null) {
                veiculoBanco.setModelo(veiculo.getModelo());
                veiculoBanco.setTipo(veiculo.getTipo());
                veiculoBanco.setPlaca(veiculo.getPlaca());
                repositorioVeiuculo.save(veiculoBanco);
                return new ResponseEntity<>("Veículo atualizado com sucesso!", HttpStatus.OK);
            }  else {
                return new ResponseEntity<>("Não é possível passar valores nulos (Model, Tipo e Placa)", HttpStatus.BAD_REQUEST);
            }
        }

    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<String> excluirVeiculo(@PathVariable long id) {
        if (repositorioVeiuculo.findById(id).isPresent()) {
            repositorioVeiuculo.deleteById(id);
            return new ResponseEntity<>("Veiculo removido com sucesso!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Veiculo não encontrado!", HttpStatus.NOT_FOUND);
        }
    }

}

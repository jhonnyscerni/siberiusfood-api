package br.com.siberius.siberiusfood.api.controller;

import br.com.siberius.siberiusfood.exception.EntidadeEmUsoException;
import br.com.siberius.siberiusfood.exception.EntidadeNaoEncontradaException;
import br.com.siberius.siberiusfood.model.Cidade;
import br.com.siberius.siberiusfood.repository.CidadeRepository;
import br.com.siberius.siberiusfood.service.CidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CidadeService cidadeService;

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(cidadeRepository.findAll());
    }

    @GetMapping("/{cidadeId}")
    public ResponseEntity<?> buscar(@PathVariable Long cidadeId) {
        Optional<Cidade> cidade = cidadeRepository.findById(cidadeId);

        if (cidade != null) {
            return ResponseEntity.ok(cidade.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Cidade cidade) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(cidadeService.salvar(cidade));
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{cidadeId}")
    public ResponseEntity<?> atualizar(@RequestBody Cidade cidade, @PathVariable Long cidadeId) {
        try {

            Cidade cidadeAtual = cidadeRepository.findById(cidadeId).orElse(null);

            if (cidadeAtual != null) {
                BeanUtils.copyProperties(cidade, cidadeAtual, "id");
                cidadeAtual = cidadeService.salvar(cidadeAtual);
                return ResponseEntity.ok(cidadeAtual);
            }
            return ResponseEntity.notFound().build();

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{cidadeId}")
    public ResponseEntity<?> remover(@PathVariable Long cidadeId) {
        try {
            cidadeService.excluir(cidadeId);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();

        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }

}

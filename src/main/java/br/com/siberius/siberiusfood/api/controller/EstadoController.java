package br.com.siberius.siberiusfood.api.controller;

import br.com.siberius.siberiusfood.exception.EntidadeEmUsoException;
import br.com.siberius.siberiusfood.exception.EntidadeNaoEncontradaException;
import br.com.siberius.siberiusfood.model.Estado;
import br.com.siberius.siberiusfood.repository.EstadoRepository;
import br.com.siberius.siberiusfood.service.EstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private EstadoService estadoService;

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(estadoRepository.listar());
    }

    @GetMapping("/{estadoId}")
    public ResponseEntity<?> buscar(@PathVariable Long estadoId) {

        Estado estado = estadoRepository.buscar(estadoId);

        if (estado != null) {
            return ResponseEntity.ok(estado);
        }
        return ResponseEntity.notFound().build();

    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Estado estado) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estadoService.salvar(estado));
    }

    @PutMapping("/{estadoId}")
    public ResponseEntity<?> atualizar(@PathVariable Long estadoId, @RequestBody Estado estado) {
        Estado estadoAtualizar = estadoRepository.buscar(estadoId);

        if (estadoAtualizar != null) {
            BeanUtils.copyProperties(estado, estadoAtualizar, "id");
            return ResponseEntity.ok(estadoService.salvar(estadoAtualizar));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{estadoId}")
    public ResponseEntity<?> remover(@PathVariable Long estadoId) {
        try {
            estadoService.excluir(estadoId);
            return ResponseEntity.noContent().build();

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();

        } catch (EntidadeEmUsoException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}

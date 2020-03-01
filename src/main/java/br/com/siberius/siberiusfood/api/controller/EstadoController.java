package br.com.siberius.siberiusfood.api.controller;

import br.com.siberius.siberiusfood.api.assembler.EstadoDTOAssembler;
import br.com.siberius.siberiusfood.api.assembler.EstadoInputDTODisassembler;
import br.com.siberius.siberiusfood.api.model.EstadoDTO;
import br.com.siberius.siberiusfood.api.model.input.EstadoInputDTO;
import br.com.siberius.siberiusfood.model.Estado;
import br.com.siberius.siberiusfood.repository.EstadoRepository;
import br.com.siberius.siberiusfood.service.EstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private EstadoDTOAssembler assembler;

    @Autowired
    private EstadoInputDTODisassembler disassembler;

    @GetMapping
    public List<EstadoDTO> listar() {
        return assembler.getListEstadoDTO(estadoRepository.findAll());
    }

    @GetMapping("/{estadoId}")
    public EstadoDTO buscar(@PathVariable Long estadoId) {
        return assembler.getEstadoDTO(estadoService.buscarOuFalhar(estadoId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoDTO salvar(@RequestBody @Valid EstadoInputDTO estadoInputDTO) {

        Estado estado = disassembler.getEstadoObject(estadoInputDTO);

        return assembler.getEstadoDTO(estadoService.salvar(estado));
    }

    @PutMapping("/{estadoId}")
    public EstadoDTO atualizar(@PathVariable Long estadoId, @RequestBody @Valid EstadoInputDTO estadoInputDTO) {
        Estado estadoAtual = estadoService.buscarOuFalhar(estadoId);

        disassembler.toCopyDomainObject(estadoInputDTO, estadoAtual);

        return assembler.getEstadoDTO(estadoService.salvar(estadoAtual));
    }

    @DeleteMapping("/{estadoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long estadoId) {
        estadoService.excluir(estadoId);
    }
}

package br.com.siberius.siberiusfood.api.controller;

import br.com.siberius.siberiusfood.api.assembler.EstadoDTOAssembler;
import br.com.siberius.siberiusfood.api.assembler.EstadoInputDTODisassembler;
import br.com.siberius.siberiusfood.api.model.EstadoDTO;
import br.com.siberius.siberiusfood.api.model.input.EstadoInputDTO;
import br.com.siberius.siberiusfood.api.openapi.controller.EstadoControllerOpenApi;
import br.com.siberius.siberiusfood.model.Estado;
import br.com.siberius.siberiusfood.repository.EstadoRepository;
import br.com.siberius.siberiusfood.service.EstadoService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/estados", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoController implements EstadoControllerOpenApi {

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

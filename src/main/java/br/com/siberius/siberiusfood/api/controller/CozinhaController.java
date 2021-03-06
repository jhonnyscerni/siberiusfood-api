package br.com.siberius.siberiusfood.api.controller;

import br.com.siberius.siberiusfood.api.assembler.CozinhaDTOAssembler;
import br.com.siberius.siberiusfood.api.assembler.CozinhaInputDTODisassembler;
import br.com.siberius.siberiusfood.api.model.CozinhaDTO;
import br.com.siberius.siberiusfood.api.model.CozinhasXmlWrapper;
import br.com.siberius.siberiusfood.api.model.input.CozinhaInputDTO;
import br.com.siberius.siberiusfood.api.openapi.controller.CozinhaControllerOpenApi;
import br.com.siberius.siberiusfood.model.Cozinha;
import br.com.siberius.siberiusfood.repository.CozinhaRepository;
import br.com.siberius.siberiusfood.service.CozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedModel;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController implements CozinhaControllerOpenApi {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CozinhaService cozinhaService;

    @Autowired
    private CozinhaDTOAssembler assembler;

    @Autowired
    private CozinhaInputDTODisassembler disassembler;

    @Autowired
    private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

    @GetMapping
    public PagedModel<CozinhaDTO> listar(@PageableDefault(size = 10) Pageable pageable) {
        Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);

        PagedModel<CozinhaDTO> cozinhasPagedModel = pagedResourcesAssembler
            .toModel(cozinhasPage, assembler);

        return cozinhasPagedModel;

//        List<CozinhaDTO> cozinhasDTO = assembler.toCollectionModel(cozinhasPage.getContent());
//
//        Page<CozinhaDTO> cozinhasDTOPage = new PageImpl<>(cozinhasDTO, pageable, cozinhasPage.getTotalElements());

//        return cozinhasDTOPage;
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public CozinhasXmlWrapper listarXml() {
        return new CozinhasXmlWrapper(cozinhaRepository.findAll());
    }

    @GetMapping("/{cozinhaId}")
    public CozinhaDTO buscar(@PathVariable Long cozinhaId) {
        return assembler.toModel(cozinhaService.buscarOuFalhar(cozinhaId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaDTO adicionar(@RequestBody @Valid CozinhaInputDTO cozinhaInputDTO) {

        Cozinha cozinha = disassembler.getCozinhaObject(cozinhaInputDTO);

        return assembler.toModel(cozinhaService.salvar(cozinha));
    }

    @PutMapping("/{cozinhaId}")
    public CozinhaDTO atualizar(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaInputDTO cozinhaInputDTO) {
        Cozinha cozinhaAtual = cozinhaService.buscarOuFalhar(cozinhaId);

        disassembler.copyToDomainObject(cozinhaInputDTO, cozinhaAtual);

        return assembler.toModel(cozinhaService.salvar(cozinhaAtual));
    }

    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cozinhaId) {
        cozinhaService.excluir(cozinhaId);
    }
}

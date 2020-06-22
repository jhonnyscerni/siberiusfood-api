package br.com.siberius.siberiusfood.api.controller;

import br.com.siberius.siberiusfood.api.assembler.PedidoDTOAssembler;
import br.com.siberius.siberiusfood.api.assembler.PedidoDTODisassembler;
import br.com.siberius.siberiusfood.api.assembler.PedidoResumoDTOAssembler;
import br.com.siberius.siberiusfood.api.model.PedidoDTO;
import br.com.siberius.siberiusfood.api.model.PedidoResumoDTO;
import br.com.siberius.siberiusfood.api.model.input.PedidoInputDTO;
import br.com.siberius.siberiusfood.core.data.PageableTranslator;
import br.com.siberius.siberiusfood.exception.EntidadeNaoEncontradaException;
import br.com.siberius.siberiusfood.exception.NegocioException;
import br.com.siberius.siberiusfood.infrastructure.repository.spec.PedidoSpecs;
import br.com.siberius.siberiusfood.model.Pedido;
import br.com.siberius.siberiusfood.model.Usuario;
import br.com.siberius.siberiusfood.repository.PedidoRepository;
import br.com.siberius.siberiusfood.repository.filter.PedidoFilter;
import br.com.siberius.siberiusfood.service.PedidoService;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/pedidos")
@RestController
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoDTOAssembler assembler;

    @Autowired
    private PedidoResumoDTOAssembler assemblerResumo;

    @Autowired
    private PedidoDTODisassembler disassembler;

//    @GetMapping
//    public List<PedidoResumoDTO> listar() {
//        return assemblerResumo.getListPedidoResumoDTO(pedidoRepository.findAll());
//    }

    @GetMapping
    public Page<PedidoResumoDTO> pesquisar(PedidoFilter filter, @PageableDefault(size = 10) Pageable pageable) {

        pageable = traduzirPageable(pageable);

        Page<Pedido> pedidosPage = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filter), pageable);

        List<PedidoResumoDTO> pedidoResumoDTO = assemblerResumo.getListPedidoResumoDTO(pedidosPage.getContent());

        Page<PedidoResumoDTO> pedidoResumoDTOPage = new PageImpl<>(pedidoResumoDTO, pageable, pedidosPage.getTotalElements());

        return pedidoResumoDTOPage;
    }

    @GetMapping("/{codigoPedido}")
    public PedidoDTO buscar(@PathVariable String codigoPedido) {
        Pedido pedido = pedidoService.buscarOuFalhar(codigoPedido);

        return assembler.getPedidoDTO(pedido);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoDTO salvar(@RequestBody @Valid PedidoInputDTO pedidoInputDTO) {
        try {

            Pedido novoPedido = disassembler.getPedidoObject(pedidoInputDTO);

            // TODO pegar usuário autenticado
            novoPedido.setCliente(new Usuario());
            novoPedido.getCliente().setId(1L);

            return assembler.getPedidoDTO(pedidoService.emitir(novoPedido));

        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    //    private Pageable traduzirPageable(Pageable apiPageable) {
//        ImmutableMap mapeamento = ImmutableMap.of(
//            "codigo", "codigo",
//            "restaurante.nome", "restaurante.nome",
//            "nomeCliente", "cliente.nome",
//            "valorTotal", "valorTotal"
//        );
//
//        return PageableTranslator.translate(apiPageable, mapeamento);
//    }
    private Pageable traduzirPageable(Pageable apiPageable) {
        Map<String, String> mapeamento = new HashMap<>();
        mapeamento.put("codigo", "codigo");
        mapeamento.put("subtotal", "subtotal");
        mapeamento.put("taxaFrete", "taxaFrete");
        mapeamento.put("valorTotal", "valorTotal");
        mapeamento.put("dataCriacao", "dataCriacao");
        mapeamento.put("restaurante.nome", "restaurante.nome");
        mapeamento.put("restaurante.id", "restaurante.id");
        mapeamento.put("cliente.id", "cliente.id");
        mapeamento.put("cliente.nome", "cliente.nome");

        return PageableTranslator.translate(apiPageable, mapeamento);
    }
}

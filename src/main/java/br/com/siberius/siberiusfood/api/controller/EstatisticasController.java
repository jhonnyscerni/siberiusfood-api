package br.com.siberius.siberiusfood.api.controller;

import br.com.siberius.siberiusfood.filter.VendaDiariaFilter;
import br.com.siberius.siberiusfood.model.dto.VendaDiaria;
import br.com.siberius.siberiusfood.service.VendaQueryService;
import br.com.siberius.siberiusfood.service.VendaReportService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "/estatisticas")
@RestController
public class EstatisticasController {

    @Autowired
    private VendaQueryService vendaQueryService;

    @Autowired
    private VendaReportService vendaReportService;

    @GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filter,
        @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
        return vendaQueryService.consultarVendasDiarias(filter, timeOffset);
    }

    @GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter filter,
        @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {

        byte[] bytesPdf = vendaReportService.emitirVendasDiarias(filter, timeOffset);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_PDF)
            .headers(headers)
            .body(bytesPdf);
    }
}

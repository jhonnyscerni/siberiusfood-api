package br.com.siberius.siberiusfood.infrastructure.service.report;

import br.com.siberius.siberiusfood.filter.VendaDiariaFilter;
import br.com.siberius.siberiusfood.model.dto.VendaDiaria;
import br.com.siberius.siberiusfood.service.VendaQueryService;
import br.com.siberius.siberiusfood.service.VendaReportService;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JasperPdfVendaReportService implements VendaReportService {

    @Autowired
    private VendaQueryService vendaQueryService;

    @Override
    public byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {
        try {
            InputStream inputStream = this.getClass().getResourceAsStream(
                "/relatorios/vendas-diarias.jasper");

            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));

            List<VendaDiaria> vendasDiarias = vendaQueryService.consultarVendasDiarias(filtro, timeOffset);
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(vendasDiarias);

            JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            throw new ReportException("Não foi possível emitir relatório de vendas diárias", e);
        }
    }
}

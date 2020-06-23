package br.com.siberius.siberiusfood.service;

import br.com.siberius.siberiusfood.filter.VendaDiariaFilter;

public interface VendaReportService {

    byte[] emitirVendasDiarias(VendaDiariaFilter filter, String timeOffset);

}
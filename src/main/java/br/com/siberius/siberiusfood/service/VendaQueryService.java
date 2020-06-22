package br.com.siberius.siberiusfood.service;

import br.com.siberius.siberiusfood.filter.VendaDiariaFilter;
import br.com.siberius.siberiusfood.model.dto.VendaDiaria;
import java.util.List;

public interface VendaQueryService {

    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filter);
}

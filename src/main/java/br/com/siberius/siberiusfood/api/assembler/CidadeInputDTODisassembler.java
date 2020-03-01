package br.com.siberius.siberiusfood.api.assembler;

import br.com.siberius.siberiusfood.api.model.EstadoDTO;
import br.com.siberius.siberiusfood.api.model.input.CidadeInputDTO;
import br.com.siberius.siberiusfood.api.model.input.EstadoIdInputDTO;
import br.com.siberius.siberiusfood.model.Cidade;
import br.com.siberius.siberiusfood.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CidadeInputDTODisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Cidade getCidadeObject(CidadeInputDTO cidadeInputDTO){
        return modelMapper.map(cidadeInputDTO, Cidade.class);
    }

    public void copyToDomainObject(CidadeInputDTO cidadeInputDTO, Cidade cidade){
        // Para evitar org.hibernate.HibernateException: identifier of an instance of
        // was altered from 1 to 2
        cidade.setEstado(new Estado());
        modelMapper.map(cidadeInputDTO, cidade);
    }
}

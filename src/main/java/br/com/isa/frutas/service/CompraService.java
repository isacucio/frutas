package br.com.isa.frutas.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.isa.frutas.entity.Compra;
import br.com.isa.frutas.repository.CompraRepository;
import br.com.isa.frutas.service.exception.FrutaNotFoundException;
import br.com.isa.frutas.service.exception.PessoaNotFoundException;

@Service
public class CompraService {
    @Autowired
    private CompraRepository compraRepository = null;
    @Autowired
    private FrutaService frutaService = null;
    @Autowired
    private PessoaService pessoaService = null;

    public List<Compra> lista() {
        return compraRepository.findAll();
    }

     public boolean adicionar(Compra compraAdicionar) throws FrutaNotFoundException, PessoaNotFoundException {
        frutaService.checkAndLoadUUID(compraAdicionar.getFruta());
        pessoaService.checkAndLoadUUID(compraAdicionar.getPessoa());
        if (!compraAdicionar.getData().isBlank()) {
            compraAdicionar.setUUID(UUID.randomUUID());
            compraRepository.saveAndFlush(compraAdicionar);
            return true;
        }
        return false;
    }

    public List<Compra> findAllByDate(String data, Pageable p) {
        return compraRepository.findAllByDate(data, p);
    }

}

package br.com.isa.frutas.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.isa.frutas.entity.Cidade;
import br.com.isa.frutas.entity.Pais;
import br.com.isa.frutas.repository.PaisRepository;
import br.com.isa.frutas.service.exception.PaisNotFoundException;

@Service
public class PaisService {
    @Autowired
    private PaisRepository paisRepository = null;

    private List<Pais> paises = new ArrayList<>();

    public List<Pais> lista() {
        return paisRepository.findAll();
    }

    public boolean adicionar(Pais paisAdicionar) {
        if (!paisAdicionar.getNome().isBlank()) {
            paisAdicionar.setUUID(UUID.randomUUID());
            paisRepository.saveAndFlush(paisAdicionar);
            return true;
        }
        return false;
    }

     public boolean deletar(UUID uuid) {
        Optional<Pais> pais = paisRepository.findByUUID(uuid);
        if (pais.isPresent()) {
            paisRepository.delete(pais.get());
            return true;
        }
        return false;
    }

    public boolean editar(Pais paisEditar) {
        Optional<Pais> opPais = paisRepository.findByUUID(paisEditar.getUUID());
        if (opPais.isPresent()) {
            Pais pais = opPais.get();
            if (paisEditar.getNome() != null && !paisEditar.getNome().isBlank()) {
                pais.setNome(paisEditar.getNome());
            }
            pais.setClima(paisEditar.getClima());
            pais.setVegetacao(paisEditar.getVegetacao());
            paisRepository.saveAndFlush(pais);
            return true;
        }
        return false;
    }

    public void checkId(Cidade cidade) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkId'");
    }

    public boolean checkAndLoadUUID(Pais pais) throws PaisNotFoundException {
        if (pais == null) {
            throw new PaisNotFoundException("Pais nulo.");
        }
        Optional<Pais> opPais = paisRepository.findByUUID(pais.getUUID());
        if (!opPais.isPresent()) {
            throw new PaisNotFoundException("Pais " + pais.getUUID() + " não encontrado.");
        }
        pais.setId(opPais.get().getId());
        return true;
    }

    public List<Pais> findAllByName(String nome, Pageable p) {
        return paisRepository.findAllByName(nome.toLowerCase(), p);
    }
}

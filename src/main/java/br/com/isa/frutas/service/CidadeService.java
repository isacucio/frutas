package br.com.isa.frutas.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.isa.frutas.entity.Cidade;
import br.com.isa.frutas.repository.CidadeRepository;
import br.com.isa.frutas.service.exception.CidadeNotFoundException;

@Service
public class CidadeService {
    @Autowired
    private CidadeRepository cidadeRepository = null;

    public List<Cidade> lista() {
        return cidadeRepository.findAll();
    }

    public boolean adicionar(Cidade cidadeAdicionar) {
        if (!cidadeAdicionar.getNome().isBlank()) {
            cidadeAdicionar.setUUID(UUID.randomUUID());
            cidadeRepository.saveAndFlush(cidadeAdicionar);
            return true;
        }
        return false;
    }

    public boolean deletar(UUID uuid) {
        Optional<Cidade> cidade = cidadeRepository.findByUUID(uuid);
        if (cidade.isPresent()) {
            cidadeRepository.delete(cidade.get());
            return true;
        }
        return false;
    }

    public boolean editar(Cidade cidadeEditar) {
        Optional<Cidade> opCidade = cidadeRepository.findByUUID(cidadeEditar.getUUID());
        if (opCidade.isPresent()) {
            Cidade cidade = opCidade.get();
            if (cidadeEditar.getNome() != null && !cidadeEditar.getNome().isBlank()) {
                cidade.setNome(cidadeEditar.getNome());
            }
            cidade.setNumHab(cidadeEditar.getNumHab());
            cidadeRepository.saveAndFlush(cidade);
            return true;
        }
        return false;
    }

    public boolean checkAndLoadUUID(Cidade cidade) throws CidadeNotFoundException {
        if (cidade == null) {
            throw new CidadeNotFoundException("Cidade nula.");
        }
        Optional<Cidade> opCidade = cidadeRepository.findByUUID(cidade.getUUID());
        if (!opCidade.isPresent()) {
            throw new CidadeNotFoundException("Cidade " + cidade.getUUID() + " não encontrada.");
        }
        cidade.setId(opCidade.get().getId());
        return true;
    }

    public List<Cidade> findAllByName(String nome, Pageable p) {
        return cidadeRepository.findAllByName(nome.toLowerCase(), p);
    }
}

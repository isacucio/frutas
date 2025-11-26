package br.com.isa.frutas.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.isa.frutas.entity.Pessoa;
import br.com.isa.frutas.repository.PessoaRepository;
import br.com.isa.frutas.service.exception.PaisNotFoundException;
import br.com.isa.frutas.service.exception.PessoaNotFoundException;

@Service
public class PessoaService {
    @Autowired
    private PessoaRepository pessoaRepository = null;
    @Autowired
    private PaisService paisService = null;

    public List<Pessoa> lista()  {
        return pessoaRepository.findAll();
    }

    public boolean adicionar(Pessoa pessoaAdicionar) throws PaisNotFoundException {
        paisService.checkAndLoadUUID(pessoaAdicionar.getPais());
        if (!pessoaAdicionar.getNome().isBlank()) {
            pessoaAdicionar.setUUID(UUID.randomUUID());
            pessoaRepository.saveAndFlush(pessoaAdicionar);
            return true;
        }
        return false;
    }

    public boolean deletar(UUID uuid) {
        Optional<Pessoa> pessoa = pessoaRepository.findByUUID(uuid);
        if(pessoa.isPresent()) {
            pessoaRepository.delete(pessoa.get());
            return true;
        }
        return false;
    }

    public boolean editar(Pessoa pessoaEditar) {
        Optional <Pessoa> opPessoa = pessoaRepository.findByUUID(pessoaEditar.getUUID());
        if (opPessoa.isPresent()) {
            Pessoa pessoa = opPessoa.get();
            if (pessoaEditar.getNome() != null && !pessoaEditar.getNome().isBlank()) {
                pessoa.setNome(pessoaEditar.getNome());
            }
            pessoa.setEtnia(pessoaEditar.getEtnia());
            pessoa.setGenero(pessoaEditar.getGenero());
            pessoaRepository.saveAndFlush(pessoa);
            return true;
        }
        return false;
    }

    public boolean checkAndLoadUUID(Pessoa pessoa) throws PessoaNotFoundException {
        if (pessoa == null) {
            throw new PessoaNotFoundException("Pessoa nula.");
        }
        Optional<Pessoa> opPessoa = pessoaRepository.findByUUID(pessoa.getUUID());
        if (!opPessoa.isPresent()) {
            throw new PessoaNotFoundException("Pessoa " + pessoa.getUUID() + " não encontrada.");
        }
        pessoa.setId(opPessoa.get().getId());
        return true;
    }

    public List<Pessoa> findAllByName(String nome, Pageable p) {
        return pessoaRepository.findAllByName(nome.toLowerCase(), p);
    }
}

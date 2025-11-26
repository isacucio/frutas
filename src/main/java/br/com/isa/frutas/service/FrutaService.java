package br.com.isa.frutas.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.isa.frutas.entity.Fruta;
import br.com.isa.frutas.repository.FrutaRepository;
import br.com.isa.frutas.service.exception.CidadeNotFoundException;
import br.com.isa.frutas.service.exception.FrutaNotFoundException;
import br.com.isa.frutas.service.exception.PaisNotFoundException;

@Service
public class FrutaService {
    @Autowired
    private FrutaRepository frutaRepository = null;
    @Autowired
    private CidadeService cidadeService = null;
    @Autowired
    private PaisService paisService = null;

    public List<Fruta> lista()  {
        return frutaRepository.findAll();
    }

    public boolean adicionar(Fruta frutaAdicionar) throws CidadeNotFoundException, PaisNotFoundException {
        cidadeService.checkAndLoadUUID(frutaAdicionar.getCidade());
        paisService.checkAndLoadUUID(frutaAdicionar.getPais());
        if (!frutaAdicionar.getNome().isBlank()) {
            frutaAdicionar.setUUID(UUID.randomUUID());
            frutaRepository.saveAndFlush(frutaAdicionar);
            return true;
        }
        return false;
    }

    
    public boolean verificaCor(String cor ) {
        return cor.equalsIgnoreCase("Laranja")
            || cor.equalsIgnoreCase("Verde")
            || cor.equalsIgnoreCase("Roxo");
    }

    public boolean deletar(UUID uuid) {
        Optional<Fruta> fruta = frutaRepository.findByUUID(uuid);
        if(fruta.isPresent()) {
            frutaRepository.delete(fruta.get());
            return true;
        }
        return false;
    }

    public boolean editar(Fruta frutaEditar) {
        Optional <Fruta> opFruta = frutaRepository.findByUUID(frutaEditar.getUUID());
        if (opFruta.isPresent()) {
            Fruta fruta = opFruta.get();
            if (frutaEditar.getNome() != null && !frutaEditar.getNome().isBlank()) {
                fruta.setNome(frutaEditar.getNome());
            }
            fruta.setSemente(frutaEditar.getSemente());
            fruta.setFormato(frutaEditar.getFormato());
            fruta.setTextura(frutaEditar.getTextura());
            fruta.setCor(frutaEditar.getCor());
            frutaRepository.saveAndFlush(fruta);
            return true;
        }
        return false;
    }

    public boolean checkAndLoadUUID(Fruta fruta) throws FrutaNotFoundException {
        if (fruta == null) {
            throw new FrutaNotFoundException("Fruta nula.");
        }
        Optional<Fruta> opFruta = frutaRepository.findByUUID(fruta.getUUID());
        if (!opFruta.isPresent()) {
            throw new FrutaNotFoundException("Fruta " + fruta.getUUID() + " não encontrada.");
        }
        fruta.setId(opFruta.get().getId());
        return true;
    }

    public List<Fruta> findAllByName(String nome, Pageable p) {
        return frutaRepository.findAllByName(nome.toLowerCase(), p);
    }
       

}

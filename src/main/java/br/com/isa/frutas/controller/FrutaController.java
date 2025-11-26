package br.com.isa.frutas.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.isa.frutas.dto.CidadeListaDTO;
import br.com.isa.frutas.dto.ErroDTO;
import br.com.isa.frutas.dto.FrutaAdicionarDTO;
import br.com.isa.frutas.dto.FrutaEditarDTO;
import br.com.isa.frutas.dto.FrutaListaDTO;
import br.com.isa.frutas.dto.PaisListaDTO;
import br.com.isa.frutas.dto.SucessoDTO;
import br.com.isa.frutas.entity.Cidade;
import br.com.isa.frutas.entity.Formato;
import br.com.isa.frutas.entity.Fruta;
import br.com.isa.frutas.entity.Pais;
import br.com.isa.frutas.service.FrutaService;
import br.com.isa.frutas.service.exception.CidadeNotFoundException;
import br.com.isa.frutas.service.exception.PaisNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/fruta")
@Tag(name = "Fruta", description = "Gestão de frutas")
public class FrutaController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FrutaController.class);
    @Autowired
    private FrutaService frutaService = null;
    private List<Fruta> frutas = new ArrayList<>();

    @Operation(summary = "Listar frutas.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Fruta listada com sucesso."),
        @ApiResponse(responseCode = "400", description = "Fruta não encontrada.")
    })
    @GetMapping("/lista")
    public List<FrutaListaDTO> lista(@RequestParam String nome, 
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "nome") String ordenacaoCampo,
            @RequestParam(defaultValue = "asc") String ordenacaoDirecao
        ) {
        List<FrutaListaDTO> frutas = new ArrayList<>();
        for (Fruta fruta : frutaService.findAllByName(nome, 
            PageRequest.of(page -1, 2)
                .withSort(Sort.Direction.fromString(ordenacaoDirecao), ordenacaoCampo)
            )) {
            PaisListaDTO pais = new PaisListaDTO();
            pais.setUuid(fruta.getPais().getUUID());
            pais.setNome(fruta.getPais().getNome());
            CidadeListaDTO cidade = new CidadeListaDTO();
            cidade.setUuid(fruta.getCidade().getUUID());
            cidade.setNome(fruta.getCidade().getNome());
            frutas.add(new FrutaListaDTO(fruta.getUUID(), 
                fruta.getNome(), pais, cidade)
            );
        }
        return frutas;
    }

    @Operation(summary = "Adicionar frutas.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Fruta adicionada com sucesso."),
        @ApiResponse(responseCode = "400", description = "Fruta não encontrada.")
    })
    @PostMapping()
    public ResponseEntity<?> adicionar(@Valid @RequestBody FrutaAdicionarDTO frutaAdicionar) {
        Fruta fruta = new Fruta();
        fruta.setNome(frutaAdicionar.getNome());
        fruta.setSemente(frutaAdicionar.getSemente());
        fruta.setTextura(frutaAdicionar.getTextura());
        if (frutaAdicionar.getCidade() != null) {
            Cidade cidade = new Cidade();
            cidade.setUUID(frutaAdicionar.getCidade().getUuid());
            fruta.setCidade(cidade);
        }
        if (frutaAdicionar.getPais() != null) {
            Pais pais = new Pais();
            pais.setUUID(frutaAdicionar.getPais().getUuid());
            fruta.setPais(pais);
        }
        fruta.setFormato(Formato.from(frutaAdicionar.getFormato()));
        try {
            if (frutaService.adicionar(fruta)) {
                return ResponseEntity.ok()
                    .body(new SucessoDTO(true, "Fruta adicionada"));
            }
            return ResponseEntity.badRequest()
                .header("Teste", "Texto qualquer")
                .body(new ErroDTO("fruta-invalida", "Fruta inválida!")); 
        } catch (CidadeNotFoundException e) {
            LOGGER.error("Cidade nao encontrada: " + (
                frutaAdicionar.getCidade() != null ? frutaAdicionar.getCidade().getUuid() : "Cidade nula"
            ), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErroDTO("cidade-nao-encontrada", e.getMessage()));
        } catch (PaisNotFoundException p) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErroDTO("pais-nao-encontrado", p.getMessage()));
        }
    }

    @Operation(summary = "Ignorar frutas com cores inválidas.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Fruta ignorada com sucesso."),
        @ApiResponse(responseCode = "400", description = "Fruta não encontrada.")
    })
    @PostMapping("/ignorar")
    public ResponseEntity<?> ignorar(@RequestParam("cor") String cor) {
        if (frutaService.verificaCor(cor)) {
            return ResponseEntity.badRequest()
                .body(new ErroDTO("cor-invalida", "Cor inválida"));
        }
        return ResponseEntity.ok()
            .body(new SucessoDTO(true, "Cor aceita"));
    }

    @Operation(summary = "Deletar frutas.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Fruta deletada com sucesso."),
        @ApiResponse(responseCode = "400", description = "Fruta não encontrada.")
    })
    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deletar(@PathVariable(value = "uuid") UUID uuid) {
        if (frutaService.deletar(uuid)) {
            return ResponseEntity.ok()
               .body(new SucessoDTO(true, "Fruta deletada."));
        }
        return ResponseEntity.badRequest()
            .body(new ErroDTO("nao-encontrado", "Não encontrado!"));  
    } 

    @Operation(summary = "Editar frutas.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Fruta editada com sucesso."),
        @ApiResponse(responseCode = "400", description = "Fruta não encontrada.")
    })
    @PutMapping
    public ResponseEntity<?> editar(@RequestBody FrutaEditarDTO frutaEditar) {
        Fruta fruta = new Fruta();
        fruta.setUUID(frutaEditar.getUuid());
        fruta.setNome(frutaEditar.getNome());
        fruta.setSemente(frutaEditar.getSemente());
        fruta.setCor(frutaEditar.getCor());
        fruta.setTextura(frutaEditar.getTextura());
        Pais pais = new Pais();
        pais.setUUID(frutaEditar.getPais().getUuid());
        fruta.setPais(pais);
        if (frutaService.editar(fruta)) {
            return ResponseEntity.ok()
                .body(new SucessoDTO(true,"ok"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErroDTO("no-ok", "No ok"));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(e -> {
            String fieldName = ((FieldError)e).getField();
            String errorMessage = e.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}

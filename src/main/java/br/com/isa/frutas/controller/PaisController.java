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

import br.com.isa.frutas.dto.ErroDTO;
import br.com.isa.frutas.dto.PaisAdicionarDTO;
import br.com.isa.frutas.dto.PaisEditarDTO;
import br.com.isa.frutas.dto.PaisListaDTO;
import br.com.isa.frutas.dto.SucessoDTO;
import br.com.isa.frutas.entity.Pais;
import br.com.isa.frutas.service.PaisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pais")
@Tag(name = "País", description = "Gestão de países")
public class PaisController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaisController.class);

    @Autowired
    private PaisService paisService = null;

    @Operation(summary = "Listar países.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "País listado com sucesso."),
        @ApiResponse(responseCode = "400", description = "País não encontrado.")
    })
    @GetMapping("/lista")
    public List<PaisListaDTO> lista(@RequestParam String nome, 
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "nome") String ordenacaoCampo,
            @RequestParam(defaultValue = "asc") String ordenacaoDirecao
        ) {
        List<PaisListaDTO> paises = new ArrayList<>();
        for (Pais pais : paisService.findAllByName(nome, 
            PageRequest.of(page - 1, 2)
                .withSort(Sort.Direction.fromString(ordenacaoDirecao), ordenacaoCampo)
            )) {
            paises.add(new PaisListaDTO(pais.getUUID(), pais.getNome()));
        }
        return paises;
    }

    @Operation(summary = "Adicionar países.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "País adicionado com sucesso."),
        @ApiResponse(responseCode = "400", description = "País não encontrado.")
    })
    @PostMapping()
    public ResponseEntity<?> adicionar(@Valid @RequestBody PaisAdicionarDTO paisAdicionar) {
        Pais pais = new Pais(paisAdicionar.getNome());
        pais.setClima(paisAdicionar.getClima());
        pais.setVegetacao(paisAdicionar.getVegetacao());
        if (paisService.adicionar(pais)) {
            return ResponseEntity.ok()
                .body(new SucessoDTO(true, "País adicionado"));
        }
        return ResponseEntity.badRequest()
            .body(new ErroDTO("pais-invalido", "País inválido!")); 
    }

    @Operation(summary = "Deletar países.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "País deletado com sucesso."),
        @ApiResponse(responseCode = "400", description = "País não encontrado.")
    })
    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deletar(@PathVariable(value = "uuid") UUID uuid) {
        if (paisService.deletar(uuid)) {
            return ResponseEntity.ok()
               .body(new SucessoDTO(true, "País deletado."));
        }
        return ResponseEntity.badRequest()
            .body(new ErroDTO("nao-encontrado", "Não encontrado!"));
       
    }

    @Operation(summary = "Editar países.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "País editado com sucesso."),
        @ApiResponse(responseCode = "400", description = "País não encontrado.")
    })
    @PutMapping
    public ResponseEntity<?> editar(@RequestBody PaisEditarDTO paisEditar) {
        Pais pais = new Pais(paisEditar.getNome());
        pais.setUUID(paisEditar.getUuid());
        pais.setClima(paisEditar.getClima());
        pais.setVegetacao(paisEditar.getVegetacao());
        if (paisService.editar(pais)) {
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

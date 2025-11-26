package br.com.isa.frutas.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import br.com.isa.frutas.dto.PessoaAdicionarDTO;
import br.com.isa.frutas.dto.PessoaEditarDTO;
import br.com.isa.frutas.dto.PessoaListaDTO;
import br.com.isa.frutas.dto.SucessoDTO;
import br.com.isa.frutas.entity.Pais;
import br.com.isa.frutas.entity.Pessoa;
import br.com.isa.frutas.service.PessoaService;
import br.com.isa.frutas.service.exception.PaisNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pessoa")
@Tag(name = "Pessoa", description = "Gestão de pessoas")
public class PessoaController {
    @Autowired
    private PessoaService pessoaService = null;
    private List<Pessoa> pessoas = new ArrayList<>();

    @Operation(summary = "Listar pessoas.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pessoa listada com sucesso."),
        @ApiResponse(responseCode = "400", description = "Pessoa não encontrada.")
    })
    @GetMapping("/lista")
    public List<PessoaListaDTO> lista(
            @RequestParam String nome, 
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "nome") String ordenacaoCampo,
            @RequestParam(defaultValue = "asc") String ordenacaoDirecao
        ) {
        List<PessoaListaDTO> pessoas = new ArrayList<>();
        for (Pessoa pessoa : pessoaService.findAllByName(nome, 
            PageRequest.of(page - 1, 20)
                .withSort(Sort.Direction.fromString(ordenacaoDirecao), ordenacaoCampo)
            )) {
        pessoas.add(new PessoaListaDTO(pessoa.getUUID(),pessoa.getNome()));
            System.out.println(pessoa.getPais());
            pessoa.setPais(null);
        }
        return pessoas;
    }

    @Operation(summary = "Adicionar uma pessoa.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pessoa adicionada com sucesso."),
        @ApiResponse(responseCode = "400", description = "Pessoa não encontrada.")
    })

    @PostMapping()
    public ResponseEntity<?> adicionar(@Valid @RequestBody PessoaAdicionarDTO pessoaAdicionar) {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(pessoaAdicionar.getNome());
        pessoa.setEtnia(pessoaAdicionar.getEtnia());
        pessoa.setGenero(pessoaAdicionar.getGenero());
        if (pessoaAdicionar.getPais() != null) {
            Pais pais = new Pais();
            pais.setUUID(pessoaAdicionar.getPais().getUuid());
            pessoa.setPais(pais);
        }
        try {
            if (pessoaService.adicionar(pessoa)) {
                return ResponseEntity.ok()
                    .body(new SucessoDTO(true, "Pessoa adicionada"));
            }
            return ResponseEntity.badRequest()
                .body(new ErroDTO("pessoa-invalida", "Pessoa inválida!")); 
        } catch (PaisNotFoundException p) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErroDTO("pais-nao-encontrado", p.getMessage()));
        }
    }

    @Operation(summary = "Deletar uma pessoa.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pessoa adicionada com sucesso."),
        @ApiResponse(responseCode = "400", description = "Pessoa não encontrada.")
    })
    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deletar(@PathVariable(value = "uuid") UUID uuid) {
        if (pessoaService.deletar(uuid)) {
            return ResponseEntity.ok()
               .body(new SucessoDTO(true, "Pessoa deletada."));
        }
        return ResponseEntity.badRequest()
            .body(new ErroDTO("nao-encontrado", "Não encontrado!"));  
    }

    @Operation(summary = "Editar uma pessoa.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pessoa criada com sucesso."),
        @ApiResponse(responseCode = "400", description = "Pessoa não encontrada.")
    })
    @PutMapping
    public ResponseEntity<?> editar(@RequestBody PessoaEditarDTO pessoaEditar) {
        Pessoa pessoa = new Pessoa();
        pessoa.setUUID(pessoaEditar.getUuid());
        pessoa.setNome(pessoaEditar.getNome());
        pessoa.setEtnia(pessoaEditar.getEtnia());
        pessoa.setGenero(pessoaEditar.getGenero());
        Pais pais = new Pais();
        pais.setUUID(pessoaEditar.getPais().getUuid());
        pessoa.setPais(pais);
        if (pessoaService.editar(pessoa)) {
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

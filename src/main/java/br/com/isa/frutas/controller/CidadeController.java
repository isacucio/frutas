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

import br.com.isa.frutas.dto.CidadeAdicionarDTO;
import br.com.isa.frutas.dto.CidadeEditarDTO;
import br.com.isa.frutas.dto.CidadeListaDTO;
import br.com.isa.frutas.dto.ErroDTO;
import br.com.isa.frutas.dto.SucessoDTO;
import br.com.isa.frutas.entity.Cidade;
import br.com.isa.frutas.service.CidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/cidade")
@Tag(name = "Cidade", description = "Gestão de cidades")
public class CidadeController {
    @Autowired
    private CidadeService cidadeService = null;

    @Operation(summary = "Listar cidades.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cidade listada com sucesso."),
        @ApiResponse(responseCode = "400", description = "Cidade não encontrada.")
    })
    @GetMapping("/lista")
    public List<CidadeListaDTO> lista(@RequestParam String nome, 
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "nome") String ordenacaoCampo,
            @RequestParam(defaultValue = "asc") String ordenacaoDirecao
        ) {
        List<CidadeListaDTO> cidades = new ArrayList<>();
        for (Cidade cidade : cidadeService.findAllByName(nome, 
            PageRequest.of(page - 1, 2)
                .withSort(Sort.Direction.fromString(ordenacaoDirecao), ordenacaoCampo)
            )) {
            cidades.add(new CidadeListaDTO(cidade.getUUID(), cidade.getNome()));
        }
        return cidades;
    }

    @Operation(summary = "Adicionar cidades.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cidade adicioada com sucesso."),
        @ApiResponse(responseCode = "400", description = "Cidade não encontrada.")
    })
    @PostMapping()
    public ResponseEntity<?> adicionar(@Valid @RequestBody CidadeAdicionarDTO cidadeAdicionar) {
        Cidade cidade = new Cidade();
        cidade.setNome(cidadeAdicionar.getNome());
        cidade.setNumHab(cidadeAdicionar.getNumHab());
        if (cidadeService.adicionar(cidade)) {
            return ResponseEntity.ok()
                .body(new SucessoDTO(true, "Cidade adicionada"));
        }
        return ResponseEntity.badRequest()
            .body(new ErroDTO("cidade-invalida", "Cidade inválida!")); 
    }

    @Operation(summary = "Deletar cidades.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cidade deletada com sucesso."),
        @ApiResponse(responseCode = "400", description = "Cidade não encontrada.")
    })
    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deletar(@PathVariable(value = "uuid") UUID uuid) {
        if (cidadeService.deletar(uuid)) {
            return ResponseEntity.ok()
               .body(new SucessoDTO(true, "Cidade deletada."));
        }
        return ResponseEntity.badRequest()
            .body(new ErroDTO("nao-encontrada", "Não encontrada!"));
       
    }

    @Operation(summary = "Editar cidades.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cidade editada com sucesso."),
        @ApiResponse(responseCode = "400", description = "Cidade não encontrada.")
    })
    @PutMapping
    public ResponseEntity<?> editar(@RequestBody CidadeEditarDTO cidadeEditar) {
        Cidade cidade = new Cidade();
        cidade.setNome(cidadeEditar.getNome());
        cidade.setUUID(cidadeEditar.getUuid());
        cidade.setNumHab(cidadeEditar.getNumHab());
        if (cidadeService.editar(cidade)) {
            return ResponseEntity.ok()
                .body(new SucessoDTO(true, "ok"));
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

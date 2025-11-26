package br.com.isa.frutas.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.isa.frutas.dto.CompraAdicionarDTO;
import br.com.isa.frutas.dto.CompraListaDTO;
import br.com.isa.frutas.dto.ErroDTO;
import br.com.isa.frutas.dto.SucessoDTO;
import br.com.isa.frutas.entity.Compra;
import br.com.isa.frutas.entity.Fruta;
import br.com.isa.frutas.entity.Pessoa;
import br.com.isa.frutas.service.CompraService;
import br.com.isa.frutas.service.exception.FrutaNotFoundException;
import br.com.isa.frutas.service.exception.PessoaNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/compra")
@Tag(name = "Compra", description = "Gestão de compras")
public class CompraController {
    @Autowired
    private CompraService compraService = null;

    @Operation(summary = "Listar compras.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Compra listada com sucesso."),
        @ApiResponse(responseCode = "400", description = "Compra não encontrada.")
    })
    @GetMapping("/lista")
    public List<CompraListaDTO> lista(@RequestParam String nome, 
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "data") String ordenacaoCampo,
            @RequestParam(defaultValue = "asc") String ordenacaoDirecao
        ) {
        List<CompraListaDTO> compras = new ArrayList<>();
        for (CompraListaDTO compra : compras) {
            if (compra.getFruta() != null) {
                compra.getFruta().setCompra(null);
            }
            if (compra.getPessoa() != null) {
                compra.getPessoa().setCompra(null);
            }
        }
        return compras;
    }

    @Operation(summary = "Adicionar compras.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Compra adicionada com sucesso."),
        @ApiResponse(responseCode = "400", description = "Compra não encontrada.")
    })
    @PostMapping()
    public ResponseEntity<?> adicionar(@Valid @RequestBody CompraAdicionarDTO compraAdicionar) {
        Compra compra = new Compra();
        compra.setUUID(compraAdicionar.getUuid());
        compra.setData(compraAdicionar.getData());
        compra.setNumItens(compraAdicionar.getNumItens());
        if (compraAdicionar.getFruta() != null) {
            Fruta fruta = new Fruta();
            fruta.setId(compraAdicionar.getFruta().getId());
            compra.setFruta(fruta);
        }
        if (compraAdicionar.getPessoa() != null) {
            Pessoa pessoa = new Pessoa();
            pessoa.setUUID(compraAdicionar.getPessoa().getUuid());
            compra.setPessoa(pessoa);
        }
        
        try {
            if (compraService.adicionar(compra)) {
                return ResponseEntity.ok()
                    .body(new SucessoDTO(true, "Compra adicionada"));
            } 
            return ResponseEntity.badRequest()
                .body(new ErroDTO("compra-invalida", "Compra inválida!")); 
        } catch(FrutaNotFoundException f) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErroDTO("fruta-nao-encontrada", f.getMessage()));
        } catch (PessoaNotFoundException p) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErroDTO("compra-nao-encontrada", p.getMessage()));
        }
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

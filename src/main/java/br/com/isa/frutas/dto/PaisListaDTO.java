package br.com.isa.frutas.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(
    name = "Lista países", 
    description = "Estrutura de dados para listar países."
)
public class PaisListaDTO {
    private UUID uuid;
    @Schema(description = "Nome do país.", example = "Japão")
    private String nome;

}

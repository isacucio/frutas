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
    name = "Listar cidades", 
    description = "Estrutura de dados para listar cidades."
)
public class CidadeListaDTO {
    private UUID uuid;
    @Schema(description = "Nome da cidade.", example = "São Paulo")
    private String nome;
}

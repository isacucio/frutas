package br.com.isa.frutas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(
    name = "Adicionar cidades", 
    description = "Estrutura de dados para adicionar cidades."
)
public class CidadeAdicionarDTO {
    @NotBlank(message = "nome obrigatório")
    @Schema(description = "Nome da cidade.", example = "São Paulo")
    public String nome;
    @Schema(description = "Número de habitantes da cidade.", example = "3")
    public int numHab;
}

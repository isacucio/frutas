package br.com.isa.frutas.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(
    name = "Editar cidades", 
    description = "Estrutura de dados para editar cidades."
)
public class CidadeEditarDTO {
    private UUID uuid;
    @Schema(description = "Nome da cidade.", example = "São Paulo")
    @NotBlank(message = "nome obrigatório")
    @Size(min = 4, message = "nome curto")
    private String nome;
    @Schema(description = "Número de habitantes da cidade.", example = "3")
    private int numHab;

}

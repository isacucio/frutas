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
    name = "Listar frutas", 
    description = "Estrutura de dados para listar frutas."
)
public class FrutaListaDTO {
    private UUID uuid;
     @Schema(description = "Nome da fruta.", example = "Amora")
    private String nome;
    private PaisListaDTO pais;
    private CidadeListaDTO cidade;
}

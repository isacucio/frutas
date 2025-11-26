package br.com.isa.frutas.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(
    name = "Edita países", 
    description = "Estrutura de dados para editar países."
)
public class PaisEditarDTO {
    private UUID uuid;
    @Schema(description = "Nome do país.", example = "Japão")
    @NotBlank(message = "nome obrigatório")
    private String nome;
    @Schema(description = "Nome do país.", example = "Japão")
    private String clima;
    @Schema(description = "Vegetação do país.", example = "Floresta Subtropical")
    private String vegetacao;

}

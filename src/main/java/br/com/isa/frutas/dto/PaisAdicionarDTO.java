package br.com.isa.frutas.dto;

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
    name = "Adiciona países", 
    description = "Estrutura de dados para adicionar países."
)
public class PaisAdicionarDTO {
    @NotBlank(message = "nome obrigatório")
    @Size(min = 3, message = "nome curto")
    @Schema(description = "Nome do país.", example = "Japão")
    private String nome;
    @NotBlank(message = "clima obrigatório.")
    @Schema(description = "Clima do país.", example = "Temperado oceânico")
    private String clima;
    @Schema(description = "Vegetação do país.", example = "Floresta Subtropical")
    private String vegetacao;

}

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
    name = "Adiciona pessoa", 
    description = "Estrutura de dados para adicionar pessoas."
)
public class PessoaAdicionarDTO {
    @NotBlank(message = "nome obrigatório")
    @Schema(description = "Nome da pessoa.", example = "Marco Aurélio")
    private String nome;
    @Schema(description = "Gênero da pessoa.", example = "Masculino")
    private String genero;
    @Schema(description = "Etnia da pessoa.", example = "Eslavo")
    private String etnia;
    private PaisUuidDTO pais;
    private CompraAdicionarDTO compra;

}

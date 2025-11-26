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
    name = "Edita pessoa", 
    description = "Estrutura de dados para editar pessoas."
)
public class PessoaEditarDTO {
    private UUID uuid;
    @Schema(description = "Nome da pessoa.", example = "Marco Aurélio")
    @NotBlank(message = "nome obrigatório")
    @Size(min = 2, message = "nome curto")
    private String nome;
    @Schema(description = "Etnia da pessoa.", example = "Eslavo")
    private String etnia;
    @Schema(description = "Gênero da pessoa.", example = "Masculino")
    private String genero;
    private PaisUuidDTO pais;

}

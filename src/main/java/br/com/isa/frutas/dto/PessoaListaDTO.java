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
    name = "Lista pessoas", 
    description = "Estrutura de dados para listar pessoas."
)
public class PessoaListaDTO {
    public UUID uuid;
    @Schema(description = "Nome da pessoa.", example = "Marco Aurélio")
    public String nome;

}

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
    name = "Adiciona frutas", 
    description = "Estrutura de dados para adicionar frutas."
)
public class FrutaEditarDTO {
    private UUID uuid;
    @Schema(description = "Nome da fruta.", example = "Amora")
    @NotBlank(message = "nome obrigatório")
    @Size(min = 4, message = "nome curto")
    private String nome;
    @Schema(description = "Quantidade de sementes.", example = "2")
    private int semente;
    @Schema(description = "Cor da fruta.", example = "Roxa")
    private String cor;
    @Schema(description = "Textura da fruta.", example = "Fibrosa")
    private String textura;
    @Schema(description = "Formato da fruta.", example = "Redonda")
    private String formato;
    private PaisUuidDTO pais;

}

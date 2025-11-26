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
    name = "Adiciona frutas", 
    description = "Estrutura de dados para adicionar frutas."
)
public class FrutaAdicionarDTO {
    @NotBlank(message = "nome obrigatório")
    @Schema(description = "Nome da fruta.", example = "Amora")
    private String nome;
    @Schema(description = "Quantidade de sementes.", example = "2")
    private int semente; 
    @Schema(description = "Textura da fruta.", example = "Fibrosa")
    private String textura;
    @Schema(description = "Formato da fruta.", example = "Redonda")
    private String formato;
    private PaisUuidDTO pais;
    private CidadeUuidDTO cidade;
    private CompraAdicionarDTO compra;
}

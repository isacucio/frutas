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
    name = "Adicionar compras", 
    description = "Estrutura de dados para adicionar compras."
)
public class CompraAdicionarDTO {
    private UUID uuid;
    @NotBlank(message = "data obrigatória")
    @Schema(description = "Data da compra.", example = "11/11/2025")
    private String data;
    @Schema(description = "Número de ítens da compra.", example = "9")
    private int numItens;
    private FrutaIdDTO fruta;
    private PessoaUuidDTO pessoa;

}

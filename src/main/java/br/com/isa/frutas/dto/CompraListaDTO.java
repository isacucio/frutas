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
    name = "Listar compras", 
    description = "Estrutura de dados para listar compras."
)
public class CompraListaDTO {
    private UUID uuid;
    @Schema(description = "Data da compra.", example = "11/11/2025")
    private String data;
    @Schema(description = "Número de ítens da compra.", example = "9")
    private int numItens;
    private FrutaAdicionarDTO fruta;
    private PessoaAdicionarDTO pessoa;

}

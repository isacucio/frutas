package br.com.isa.frutas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ErroDTO {
    private String codigo;
    private String descricao;

}

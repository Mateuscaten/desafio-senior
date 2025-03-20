package br.senior.desafiosenior.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MaiorEstadoDto {

    private String estado;

    private Long quantidadeCidades;
}

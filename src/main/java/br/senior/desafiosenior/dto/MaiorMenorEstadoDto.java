package br.senior.desafiosenior.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MaiorMenorEstadoDto {

    private MaiorEstadoDto maiorEstado;

    private MenorEstadoDto menorEstado;


}

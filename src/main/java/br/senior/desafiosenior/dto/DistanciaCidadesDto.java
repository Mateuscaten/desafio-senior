package br.senior.desafiosenior.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DistanciaCidadesDto {

    private String cidade1;

    private String cidade2;

    private Double distanciaKm;
}

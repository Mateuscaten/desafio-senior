package br.senior.desafiosenior.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CidadeDto {

    @NotEmpty(message = "Ibge é obrigatório.")
    private Long ibgeId;

    @NotEmpty(message = "UF é obrigatória.")
    private String uf;

    @NotEmpty(message = "Nome da cidade é obrigatório.")
    private String nomeCidade;

    @NotEmpty(message = "Capital é obrigatório.")
    private Boolean capital;

    @NotEmpty(message = "Longitude é obrigatória.")
    private Double longitude;

    @NotEmpty(message = "Latitude é obrigatória.")
    private Double latitude;

    private String nomeCidadeSemAcento;

    private String nomeAlternativo;

    @NotEmpty(message = "Micro Região é obrigatória.")
    private String microRegiao;

    @NotEmpty(message = "Meso Região é obrigatória.")
    private String mesoRegiao;

}

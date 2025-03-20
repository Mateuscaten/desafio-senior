package br.senior.desafiosenior.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdicionarCidadeDto {

    @NotNull(message = "Ibge é obrigatório.")
    private Long ibgeId;

    @Size(message = "Uf não pode ter mais de 2 caracteres", max = 2)
    @NotEmpty(message = "UF é obrigatória.")
    private String uf;

    @NotEmpty(message = "Nome da cidade é obrigatório.")
    private String nomeCidade;

    @NotNull(message = "Capital é obrigatório.")
    private Boolean capital;

    @NotNull(message = "Longitude é obrigatória.")
    private Double longitude;

    @NotNull(message = "Latitude é obrigatória.")
    private Double latitude;

    private String nomeAlternativo;

    @NotEmpty(message = "Micro Região é obrigatória.")
    private String microRegiao;

    @NotEmpty(message = "Meso Região é obrigatória.")
    private String mesoRegiao;
}

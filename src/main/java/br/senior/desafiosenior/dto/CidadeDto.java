package br.senior.desafiosenior.dto;

import br.senior.desafiosenior.dominio.entidade.Cidade;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CidadeDto {

    private Long id;

    @NotNull(message = "Ibge é obrigatório.")
    private Long ibgeId;

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

    private String nomeCidadeSemAcento;

    private String nomeAlternativo;

    @NotEmpty(message = "Micro Região é obrigatória.")
    private String microRegiao;

    @NotEmpty(message = "Meso Região é obrigatória.")
    private String mesoRegiao;


    public static CidadeDto converterEntidade(Cidade cidade) {
        if (cidade == null) {
            return null;
        }

        return CidadeDto
                .builder()
                .id(cidade.getId())
                .ibgeId(cidade.getIbgeId())
                .nomeCidade(cidade.getNomeCidade())
                .uf(cidade.getUf())
                .capital(cidade.getCapital())
                .microRegiao(cidade.getMicroRegiao())
                .mesoRegiao(cidade.getMesoRegiao())
                .nomeAlternativo(cidade.getNomeAlternativo())
                .nomeCidadeSemAcento(cidade.getNomeCidadeSemAcento())
                .latitude(cidade.getLatitude())
                .longitude(cidade.getLongitude())
                .build();

    }

}

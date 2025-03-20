package br.senior.desafiosenior.dominio.entidade;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Cidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ibge_id", nullable = false)
    private Long ibgeId;

    @Column(name = "uf", nullable = false, length = 2)
    private String uf;

    @Column(name = "nome_cidade", nullable = false, length = 200)
    private String nomeCidade;

    @Column(name = "capital", nullable = false)
    private Boolean capital;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "nome_cidade_sem_acento", nullable = false, length = 200)
    private String nomeCidadeSemAcento;

    @Column(name = "nome_alternativo", length = 200)
    private String nomeAlternativo;

    @Column(name = "micro_regiao", nullable = false, length = 200)
    private String microRegiao;

    @Column(name = "meso_regiao", nullable = false, length = 200)
    private String mesoRegiao;
}

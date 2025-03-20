package br.senior.desafiosenior.dominio.repositorio;

import br.senior.desafiosenior.dominio.entidade.Cidade;
import br.senior.desafiosenior.dominio.entidade.DistanciaCidadesProjecao;
import br.senior.desafiosenior.dominio.entidade.EstadoQuantidadeProjecao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {

    boolean existsByIbgeId(Long ibgeId);

    List<Cidade> findByCapitalTrueOrderByNomeCidadeAsc();

    @Query(nativeQuery = true, value = """  
            select uf, count(*) as quantidade
            from cidade
            group by uf
            order by quantidade desc
            """)
    List<EstadoQuantidadeProjecao> buscarQuantidadeEstado();

    Optional<Cidade> findByIbgeId(Long ibge);

    List<Cidade> findByUf(String estado);

    @Query(nativeQuery = true, value = """  
            select
                c1.nome_cidade_sem_acento as cidade1,
                c2.nome_cidade_sem_acento as cidade2,
                (
                    6371 * acos(
                        cos(radians(c1.latitude)) * cos(radians(c2.latitude)) *
                        cos(radians(c2.longitude) - radians(c1.longitude)) +
                        sin(radians(c1.latitude)) * sin(radians(c2.latitude))
                    )
                ) as distanciaKm
            from cidade c1
            join cidade c2
            on c1.id != c2.id
            order by distanciaKm desc
            LIMIT 1;
            """)
    Optional<DistanciaCidadesProjecao> buscarDistanciaCidades();

}

package br.senior.desafiosenior.dominio.repositorio;

import br.senior.desafiosenior.dominio.entidade.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {

    Optional<Cidade> findByUfAndNomeCidadeSemAcento(String Uf, String nomeCidade);

    List<Cidade> findByCapitalTrueOrderByNomeCidadeAsc();
}

package br.senior.desafiosenior.servico;

import br.senior.desafiosenior.dominio.entidade.Cidade;
import br.senior.desafiosenior.dominio.repositorio.CidadeRepository;
import br.senior.desafiosenior.excecao.FalhaRequisicaoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CidadeService {

    private final CidadeRepository cidadeRepository;

    public void importarArquivo(MultipartFile arquivo) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(arquivo.getInputStream(), StandardCharsets.UTF_8))) {
            String linha;
            List<Cidade> cidades = new ArrayList<>();

            br.readLine();

            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(",");

                Cidade cidade = cidadeRepository.findByUfAndNomeCidadeSemAcento(campos[1], campos[6])
                        .orElse(Cidade.builder().build());

                cidade.setIbgeId(Long.parseLong(campos[0]));
                cidade.setUf(campos[1]);
                cidade.setNomeCidade(campos[2]);
                cidade.setCapital(!campos[3].isEmpty() && Boolean.parseBoolean(campos[3]));
                cidade.setLongitude(Double.parseDouble(campos[4]));
                cidade.setLatitude(Double.parseDouble(campos[5]));
                cidade.setNomeCidadeSemAcento(campos[6]);
                cidade.setNomeAlternativo(campos[7].isEmpty() ? null : campos[7]);
                cidade.setMicroRegiao(campos[8]);
                cidade.setMesoRegiao(campos[9]);
                cidades.add(cidade);
            }

            cidadeRepository.saveAll(cidades);
        } catch (Exception e) {
            throw new FalhaRequisicaoException("Não foi possível processar o arquivo.");
        }
    }
}

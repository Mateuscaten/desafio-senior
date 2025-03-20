package br.senior.desafiosenior.servico;

import br.senior.desafiosenior.dominio.entidade.Cidade;
import br.senior.desafiosenior.dominio.entidade.DistanciaCidadesProjecao;
import br.senior.desafiosenior.dominio.entidade.EstadoQuantidadeProjecao;
import br.senior.desafiosenior.dominio.repositorio.CidadeRepository;
import br.senior.desafiosenior.dto.*;
import br.senior.desafiosenior.excecao.FalhaRequisicaoException;
import br.senior.desafiosenior.util.TextoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CidadeService {

    private final CidadeRepository cidadeRepository;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void importarArquivo(MultipartFile arquivo) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(arquivo.getInputStream(), StandardCharsets.UTF_8))) {
            String linha;
            List<Cidade> cidades = new ArrayList<>();

            br.readLine();

            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(",");

                Cidade cidade = cidadeRepository.findByIbgeId(Long.parseLong(campos[0]))
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

    @Transactional(readOnly = true)
    public List<CidadeDto> buscarCapitais() {

        List<Cidade> cidades = Optional.ofNullable(cidadeRepository.findByCapitalTrueOrderByNomeCidadeAsc())
                .orElse(Collections.emptyList());

        return cidades.stream().map(CidadeDto::converterEntidade).toList();
    }

    @Transactional(readOnly = true)
    public MaiorMenorEstadoDto buscarMaiorMenorEstado() {

        List<EstadoQuantidadeProjecao> estadoQuantidades = cidadeRepository.buscarQuantidadeEstado();
        if (estadoQuantidades.isEmpty()) {
            return MaiorMenorEstadoDto.builder().build();
        }

        EstadoQuantidadeProjecao maiorEstado = estadoQuantidades.getFirst();
        EstadoQuantidadeProjecao menorEstado = estadoQuantidades.get(estadoQuantidades.size() - 1);

        return MaiorMenorEstadoDto
                .builder()
                .maiorEstado(MaiorEstadoDto
                        .builder()
                        .estado(maiorEstado.getUf())
                        .quantidadeCidades(maiorEstado.getQuantidade())
                        .build())
                .menorEstado(MenorEstadoDto
                        .builder()
                        .estado(menorEstado.getUf())
                        .quantidadeCidades(menorEstado.getQuantidade())
                        .build())
                .build();

    }

    @Transactional(readOnly = true)
    public List<EstadoQuantidadeDto> buscarQuantidadeCidadesPorEstado() {

        List<EstadoQuantidadeProjecao> estadoQuantidades = cidadeRepository.buscarQuantidadeEstado();
        if (estadoQuantidades.isEmpty()) {
            return Collections.emptyList();
        }

        return estadoQuantidades.stream()
                .map(estados -> EstadoQuantidadeDto.builder()
                        .quantidadeCidades(estados.getQuantidade())
                        .estado(estados.getUf())
                        .build())
                .toList();
    }

    @Transactional(readOnly = true)
    public CidadeDto buscarCidadePorIbge(Long ibge) {

        Cidade cidade = cidadeRepository.findByIbgeId(ibge).orElse(null);
        if (cidade == null) {
            return null;
        }

        return CidadeDto.converterEntidade(cidade);

    }

    @Transactional(readOnly = true)
    public List<CidadeDto> buscarCidadePorEstado(String uf) {

        List<Cidade> cidades = cidadeRepository.findByUf(uf);
        if (cidades.isEmpty()) {
            return Collections.emptyList();
        }

        return cidades.stream().map(CidadeDto::converterEntidade).toList();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CidadeDto adicionarCidade(AdicionarCidadeDto adicionarCidadeDto) {

        if (cidadeRepository.existsByIbgeId(adicionarCidadeDto.getIbgeId())) {
            throw new FalhaRequisicaoException("Cidade já cadastrada.");
        }

        Cidade cidade = Cidade
                .builder()
                .ibgeId(adicionarCidadeDto.getIbgeId())
                .nomeCidade(adicionarCidadeDto.getNomeCidade())
                .latitude(adicionarCidadeDto.getLatitude())
                .longitude(adicionarCidadeDto.getLongitude())
                .mesoRegiao(adicionarCidadeDto.getMesoRegiao())
                .microRegiao(adicionarCidadeDto.getMicroRegiao())
                .uf(adicionarCidadeDto.getUf())
                .nomeAlternativo(adicionarCidadeDto.getNomeAlternativo())
                .capital(adicionarCidadeDto.getCapital())
                .nomeCidadeSemAcento(TextoUtil.removerAcentos(adicionarCidadeDto.getNomeCidade()))
                .build();

        cidadeRepository.save(cidade);
        return CidadeDto.converterEntidade(cidade);

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void excluirCidade(Long id) {
        cidadeRepository.deleteById(id);
    }

    public List<CidadeDto> filtrarColunas(MultipartFile arquivo, String coluna, String valor) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(arquivo.getInputStream(), StandardCharsets.UTF_8))) {

            String[] cabecalho = br.readLine().split(",");
            List<CidadeDto> cidades = new ArrayList<>();
            int posicaoColuna = -1;

            for (int i = 0; i < cabecalho.length; i++) {
                if (cabecalho[i].trim().equalsIgnoreCase(coluna.trim())) {
                    posicaoColuna = i;
                    break;
                }
            }

            if (posicaoColuna == -1) {
                throw new FalhaRequisicaoException("Coluna informada não foi encontrada.");
            }

            String linha;
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(",", -1);

                if (campos.length > posicaoColuna && campos[posicaoColuna].toLowerCase().contains(valor.toLowerCase())) {
                    CidadeDto cidade = CidadeDto.builder()
                            .ibgeId(Long.parseLong(campos[0]))
                            .uf(campos[1])
                            .nomeCidade(campos[2])
                            .capital(!campos[3].isEmpty() && Boolean.parseBoolean(campos[3]))
                            .longitude(Double.parseDouble(campos[4]))
                            .latitude(Double.parseDouble(campos[5]))
                            .nomeCidadeSemAcento(campos[6])
                            .nomeAlternativo(campos[7].isEmpty() ? null : campos[7])
                            .microRegiao(campos[8])
                            .mesoRegiao(campos[9])
                            .build();

                    cidades.add(cidade);
                }
            }

            return cidades;

        } catch (IOException e) {
            throw new FalhaRequisicaoException("Não foi possível filtrar o arquivo.");
        }
    }


    public QuantidadeDto quantidadeRegistroColuna(MultipartFile arquivo, String coluna) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(arquivo.getInputStream(), StandardCharsets.UTF_8))) {

            String[] cabecalho = br.readLine().split(",");
            int posicaoColuna = -1;

            for (int i = 0; i < cabecalho.length; i++) {
                if (cabecalho[i].trim().equalsIgnoreCase(coluna.trim())) {
                    posicaoColuna = i;
                    break;
                }
            }

            if (posicaoColuna == -1) {
                throw new FalhaRequisicaoException("Coluna informada não foi encontrada.");
            }

            Set<String> valoresUnicos = new HashSet<>();
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(",", -1);
                if (campos.length > posicaoColuna) {
                    valoresUnicos.add(campos[posicaoColuna].trim());
                }
            }

            return QuantidadeDto.builder().quantidade(valoresUnicos.size()).build();

        } catch (IOException e) {
            throw new FalhaRequisicaoException("Não foi possível processar a quantidade de registros da coluna.");
        }
    }

    public QuantidadeDto quantidadeTotal(MultipartFile arquivo) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(arquivo.getInputStream(), StandardCharsets.UTF_8))) {
            int totalRegistros = 0;

            br.readLine();

            while ((br.readLine()) != null) {
                totalRegistros++;
            }
            return QuantidadeDto.builder().quantidade(totalRegistros).build();

        } catch (IOException e) {
            throw new FalhaRequisicaoException("Não foi possível processar a quantidade total de registros.");
        }
    }

    @Transactional(readOnly = true)
    public DistanciaCidadesDto buscarDistanciaCidades() {
        Optional<DistanciaCidadesProjecao> distanciaCidadesProjecao = cidadeRepository.buscarDistanciaCidades();

        if (distanciaCidadesProjecao.isEmpty()) {
            return DistanciaCidadesDto.builder().build();
        }

        return DistanciaCidadesDto
                .builder()
                .cidade1(distanciaCidadesProjecao.get().getCidade1())
                .cidade2(distanciaCidadesProjecao.get().getCidade2())
                .distanciaKm(distanciaCidadesProjecao.get().getDistanciaKm())
                .build();
    }
}

package br.senior.desafiosenior;

import br.senior.desafiosenior.dominio.entidade.Cidade;
import br.senior.desafiosenior.dominio.entidade.EstadoQuantidadeProjecao;
import br.senior.desafiosenior.dominio.repositorio.CidadeRepository;
import br.senior.desafiosenior.dto.CidadeDto;
import br.senior.desafiosenior.dto.MaiorMenorEstadoDto;
import br.senior.desafiosenior.dto.QuantidadeDto;
import br.senior.desafiosenior.servico.CidadeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class CidadeServiceTest {

    @Mock
    private CidadeRepository cidadeRepository;

    @InjectMocks
    private CidadeService cidadeService;

    @Mock
    private MultipartFile multipartFile;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBuscarCapitais() {

        Cidade cidade = Cidade.builder()
                .ibgeId(123L)
                .uf("SP")
                .nomeCidade("Sao Paulo")
                .capital(true)
                .longitude(-46.633309)
                .latitude(-23.550520)
                .nomeCidadeSemAcento("Sao Paulo")
                .microRegiao("Micro")
                .mesoRegiao("Meso")
                .build();

        List<Cidade> cidades = Arrays.asList(cidade);

        when(cidadeRepository.findByCapitalTrueOrderByNomeCidadeAsc()).thenReturn(cidades);

        List<CidadeDto> resultado = cidadeService.buscarCapitais();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Sao Paulo", resultado.get(0).getNomeCidade());
    }


    @Test
    public void testBuscarMaiorMenorEstado() {

        EstadoQuantidadeProjecao maiorEstado = mock(EstadoQuantidadeProjecao.class);
        EstadoQuantidadeProjecao menorEstado = mock(EstadoQuantidadeProjecao.class);

        when(maiorEstado.getUf()).thenReturn("SP");
        when(maiorEstado.getQuantidade()).thenReturn(100L);

        when(menorEstado.getUf()).thenReturn("RJ");
        when(menorEstado.getQuantidade()).thenReturn(10L);

        List<EstadoQuantidadeProjecao> estadoQuantidades = Arrays.asList(maiorEstado, menorEstado);
        when(cidadeRepository.buscarQuantidadeEstado()).thenReturn(estadoQuantidades);

        MaiorMenorEstadoDto resultado = cidadeService.buscarMaiorMenorEstado();

        assertNotNull(resultado);
        assertEquals("SP", resultado.getMaiorEstado().getEstado());
        assertEquals(100, resultado.getMaiorEstado().getQuantidadeCidades());
        assertEquals("RJ", resultado.getMenorEstado().getEstado());
        assertEquals(10, resultado.getMenorEstado().getQuantidadeCidades());
    }

    @Test
    public void testBuscarCidadePorIbge() {
        Cidade cidade = Cidade.builder()
                .ibgeId(123L)
                .uf("SP")
                .nomeCidade("Sao Paulo")
                .capital(true)
                .longitude(-46.633309)
                .latitude(-23.550520)
                .nomeCidadeSemAcento("Sao Paulo")
                .microRegiao("Micro")
                .mesoRegiao("Meso")
                .build();

        when(cidadeRepository.findByIbgeId(123L)).thenReturn(Optional.of(cidade));

        CidadeDto resultado = cidadeService.buscarCidadePorIbge(123L);

        assertNotNull(resultado);
        assertEquals("Sao Paulo", resultado.getNomeCidade());
    }

    @Test
    public void testExcluirCidade() {
        Long cidadeId = 123L;

        cidadeService.excluirCidade(cidadeId);

        verify(cidadeRepository, times(1)).deleteById(cidadeId);
    }

    @Test
    public void testQuantidadeRegistroColuna() throws IOException {

        String arquivoCSV = "id,uf,nome,capital,longitude,latitude\n" +
                "123456,SP,Sao Paulo,true,-46.633309,-23.550520\n" +
                "123457,SP,Campinas,false,-47.073400,-23.185200\n";
        when(multipartFile.getInputStream()).thenReturn(new java.io.ByteArrayInputStream(arquivoCSV.getBytes(StandardCharsets.UTF_8)));

        QuantidadeDto resultado = cidadeService.quantidadeRegistroColuna(multipartFile, "uf");

        assertNotNull(resultado);
        assertEquals(1, resultado.getQuantidade());
    }

    @Test
    public void testQuantidadeTotal() throws IOException {

        String arquivoCSV = "id,uf,nome,capital,longitude,latitude\n" +
                "123456,SP,Sao Paulo,true,-46.633309,-23.550520\n" +
                "123457,SP,Campinas,false,-47.073400,-23.185200\n";
        when(multipartFile.getInputStream()).thenReturn(new java.io.ByteArrayInputStream(arquivoCSV.getBytes(StandardCharsets.UTF_8)));

        QuantidadeDto resultado = cidadeService.quantidadeTotal(multipartFile);

        assertNotNull(resultado);
        assertEquals(2, resultado.getQuantidade());
    }
}

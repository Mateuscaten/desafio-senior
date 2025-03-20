package br.senior.desafiosenior.controlador;

import br.senior.desafiosenior.dto.*;
import br.senior.desafiosenior.excecao.FalhaRequisicaoException;
import br.senior.desafiosenior.servico.CidadeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cidade")
public class CidadeController {

    private final CidadeService cidadeService;

    @PostMapping("/importar")
    public ResponseEntity<Void> importarArquivo(@RequestParam("arquivo") MultipartFile arquivo) {
        cidadeService.importarArquivo(arquivo);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/capital")
    public ResponseEntity<List<CidadeDto>> buscarCapitais() {
        return ResponseEntity.ok(cidadeService.buscarCapitais());
    }

    @GetMapping("/maior-menor-estado")
    public ResponseEntity<MaiorMenorEstadoDto> buscarMaiorMenorEstado() {
        return ResponseEntity.ok(cidadeService.buscarMaiorMenorEstado());
    }

    @GetMapping("/quantidade-cidade-por-estado")
    public ResponseEntity<List<EstadoQuantidadeDto>> buscarQuantidadeCidadesPorEstado() {
        return ResponseEntity.ok(cidadeService.buscarQuantidadeCidadesPorEstado());
    }

    @GetMapping("/busca-por-ibge/{ibge}")
    public ResponseEntity<CidadeDto> buscarCidadePorIbge(@PathVariable Long ibge) {
        return ResponseEntity.ok(cidadeService.buscarCidadePorIbge(ibge));
    }

    @GetMapping("/busca-por-estado/{uf}")
    public ResponseEntity<List<CidadeDto>> buscarCidadePorEstado(@PathVariable String uf) {
        return ResponseEntity.ok(cidadeService.buscarCidadePorEstado(uf));
    }

    @PostMapping
    public ResponseEntity<CidadeDto> adicionarCidade(@Valid @RequestBody AdicionarCidadeDto adicionarCidadeDto) {
        return ResponseEntity.ok(cidadeService.adicionarCidade(adicionarCidadeDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCidade(@PathVariable Long id) {
        cidadeService.excluirCidade(id);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/filtrar-coluna")
    public ResponseEntity<List<CidadeDto>> filtrarColunas(@RequestParam("arquivo") MultipartFile arquivo,
                                                          @RequestParam("coluna") String coluna,
                                                          @RequestParam("valor") String valor) {
        return ResponseEntity.ok(cidadeService.filtrarColunas(arquivo, coluna, valor));
    }

    @PostMapping("/quantidade-coluna")
    public ResponseEntity<QuantidadeDto> quantidadeRegistroColuna(@RequestParam("arquivo") MultipartFile arquivo,
                                                                  @RequestParam("coluna") String coluna) {
        return ResponseEntity.ok(cidadeService.quantidadeRegistroColuna(arquivo, coluna));
    }

    @PostMapping("/quantidade-total")
    public ResponseEntity<QuantidadeDto> quantidadeTotal(@RequestParam("arquivo") MultipartFile arquivo) {
        return ResponseEntity.ok(cidadeService.quantidadeTotal(arquivo));
    }

    @GetMapping("/distancia-cidades")
    public ResponseEntity<DistanciaCidadesDto> buscarDistanciaCidades() {
        return ResponseEntity.ok(cidadeService.buscarDistanciaCidades());
    }

}
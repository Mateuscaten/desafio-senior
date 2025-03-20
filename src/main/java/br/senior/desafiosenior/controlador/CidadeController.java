package br.senior.desafiosenior.controlador;

import br.senior.desafiosenior.excecao.FalhaRequisicaoException;
import br.senior.desafiosenior.servico.CidadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    
}

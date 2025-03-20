package br.senior.desafiosenior.controlador;

import br.senior.desafiosenior.dto.ErroPadrao;
import br.senior.desafiosenior.excecao.DadosNaoEncontradosException;
import br.senior.desafiosenior.excecao.FalhaRequisicaoException;
import br.senior.desafiosenior.excecao.IntegridadeDadosExeption;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ErroGlobalController {

    @ExceptionHandler({DadosNaoEncontradosException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErroPadrao> dadosNaoEncontrados(DadosNaoEncontradosException ex, HttpServletRequest request) {
        final HttpStatus status = HttpStatus.NOT_FOUND;
        return ResponseEntity
                .status(status)
                .body(ErroPadrao
                        .builder()
                        .status(status.toString())
                        .mensagens(Collections.singletonList(ex.getMessage()))
                        .caminho(request.getRequestURI())
                        .build()
                );
    }

    @ExceptionHandler({IntegridadeDadosExeption.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErroPadrao> conflito(IntegridadeDadosExeption ex, HttpServletRequest request) {
        final HttpStatus status = HttpStatus.CONFLICT;
        return ResponseEntity
                .status(status)
                .body(ErroPadrao
                        .builder()
                        .status(status.toString())
                        .mensagens(Collections.singletonList(ex.getMessage()))
                        .caminho(request.getRequestURI())
                        .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroPadrao> validationException(MethodArgumentNotValidException e, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErroPadrao
                        .builder()
                        .status(HttpStatus.BAD_REQUEST.name())
                        .mensagens(
                                e.getBindingResult()
                                        .getFieldErrors()
                                        .stream()
                                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                        .collect(Collectors.toList())
                        )
                        .caminho(request.getRequestURI())
                        .build()
                );
    }

    @ExceptionHandler({FalhaRequisicaoException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErroPadrao> erro4xx(FalhaRequisicaoException excecao, HttpServletRequest requisicao) {
        final HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity
                .status(status)
                .body(ErroPadrao
                        .builder()
                        .status(status.toString())
                        .mensagens(Collections.singletonList(excecao.getMessage()))
                        .caminho(requisicao.getRequestURI())
                        .build());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErroPadrao> erroGenerico(Exception excecao, HttpServletRequest requisicao) {
        final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity
                .status(status)
                .body(ErroPadrao
                        .builder()
                        .status(status.toString())
                        .mensagens(Collections.singletonList(excecao.getMessage()))
                        .caminho(requisicao.getRequestURI())
                        .build());
    }
}

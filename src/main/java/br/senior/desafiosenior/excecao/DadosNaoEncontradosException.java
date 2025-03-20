package br.senior.desafiosenior.excecao;

import java.io.Serial;

public class DadosNaoEncontradosException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public DadosNaoEncontradosException(String mensagem) {
        super(mensagem);
    }
}

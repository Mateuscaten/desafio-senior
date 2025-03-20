package br.senior.desafiosenior.excecao;

import java.io.Serial;

public class IntegridadeDadosExeption extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public IntegridadeDadosExeption(String mensagem) {
        super(mensagem);
    }
}

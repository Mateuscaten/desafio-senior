package br.senior.desafiosenior.excecao;

import java.io.Serial;

public class SistemaException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -7207993260333619283L;

    public SistemaException() {
        super();
    }

    public SistemaException(String mensagem, Throwable throwable) {
        super(mensagem, throwable);
    }

    public SistemaException(Throwable causa) {
        super(causa);
    }

    public SistemaException(String mensagem) {
        this(mensagem, null);
    }

}

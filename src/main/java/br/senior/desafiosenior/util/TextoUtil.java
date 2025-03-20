package br.senior.desafiosenior.util;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class TextoUtil {

    private static final Pattern ACENTOS = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");

    public static String removerAcentos(String texto) {
        if (texto == null) {
            return null;
        }
        String normalizado = Normalizer.normalize(texto, Normalizer.Form.NFD);
        return ACENTOS.matcher(normalizado).replaceAll("");
    }
}

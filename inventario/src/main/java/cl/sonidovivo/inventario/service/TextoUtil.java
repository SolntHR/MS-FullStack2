package cl.sonidovivo.inventario.service;

import java.text.Normalizer;

public class TextoUtil {
    
    private TextoUtil(){}

    static String normalizar(String texto){
        if (texto == null) return "";
        return Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll("\\p{M}", "").toLowerCase().trim();
    }

    static String vacioANull(String valor) {
        if (valor == null) return null;
        String limpio = valor.trim();
        return limpio.isEmpty() ? null : limpio;
    }
}

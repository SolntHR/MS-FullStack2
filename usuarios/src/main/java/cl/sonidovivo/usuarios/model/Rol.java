package cl.sonidovivo.usuarios.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Rol {
    CLIENTE("Cliente"),
    ADMINISTRADOR("Administrador");

    private final String etiqueta;

    Rol(String etiqueta){
        this.etiqueta = etiqueta;
    }

    @JsonValue
    public String getEtiqueta(){
        return etiqueta;
    }

    @JsonCreator
    public static Rol desdeTexto(String texto){
        if(texto==null){
            return null;
        }
        for (Rol rol : values()){
            if(rol.etiqueta.equalsIgnoreCase(texto.trim()) || rol.name().equalsIgnoreCase(texto.trim())){
                return rol;
            }
        }
        throw new IllegalArgumentException("Rol no valido. Usar 'Cliente' o 'Administrador'.");
    }
}

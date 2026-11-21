package cl.sonidovivo.usuarios.validacion;

public final class Reglas {

    private Reglas() {
    }

    public static final String PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";
    public static final String PASSWORD_MENSAJE =
            "La contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula y un número.";

    public static final String TELEFONO = "^\\+?[0-9 ]{8,15}$";
    public static final String TELEFONO_MENSAJE = "El teléfono debe tener entre 8 y 15 dígitos.";
}

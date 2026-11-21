package cl.sonidovivo.usuarios.validacion;

public final class ValidadorRut {
    private ValidadorRut(){}

    public static String limpiar(String rut){
        return rut == null ? "" : rut.replaceAll("[.\\-\\s]","").toUpperCase();
    }

    public static String normalizar(String rut){
        String limpio = limpiar(rut);
        if (limpio.length()< 2){
            return limpio;
        }
        return limpio.substring(0, limpio.length()-1) + "-" + limpio.charAt(limpio.length()-1);
    }

    public static boolean esValido(String rut){
        String limpio = limpiar(rut);
        if (limpio.length() < 8 || !limpio.matches("^[0-9]+[0-9K]$")) {
            return false;
        }
        String cuerpo = limpio.substring(0, limpio.length() - 1);
        char dvIngresado = limpio.charAt(limpio.length() - 1);

        int suma = 0;
        int multiplicador = 2;
        for (int i = cuerpo.length() - 1; i >= 0; i--) {
            suma += Character.getNumericValue(cuerpo.charAt(i)) * multiplicador;
            multiplicador = multiplicador == 7 ? 2 : multiplicador + 1;
        }
        int dvCalculado = 11 - (suma % 11);
        char dvEsperado = dvCalculado == 11 ? '0' : dvCalculado == 10 ? 'K' : Character.forDigit(dvCalculado, 10);
        return dvEsperado == dvIngresado;
    }
}

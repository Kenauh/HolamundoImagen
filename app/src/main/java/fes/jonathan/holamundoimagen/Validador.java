package fes.jonathan.holamundoimagen;

public class Validador {

    public static boolean esNombreValido(String texto) {
        return texto.matches("[a-zA-ZáéíóúÁÉÍÓÚüÜ ]+");
    }

    public static boolean esCorreoValido(String correo) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches();
    }

    public static boolean esContraseniaValida(String pass) {
        return pass.length() >= 8;
    }

    public static boolean largoValido(String texto, int min, int max) {
        return texto.length() >= min && texto.length() <= max;
    }
}

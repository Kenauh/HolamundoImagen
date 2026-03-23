package fes.jonathan.holamundoimagen;

public class Validador {

    // Solo letras y espacios — sin números ni caracteres especiales
    public static boolean esNombreValido(String texto) {
        return texto.matches("[a-zA-ZáéíóúÁÉÍÓÚüÜ ]+");
    }

    // Formato de correo válido
    public static boolean esCorreoValido(String correo) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches();
    }

    // Contraseña mínimo 8 caracteres
    public static boolean esContraseniaValida(String pass) {
        return pass.length() >= 8;
    }

    // Largo general
    public static boolean largoValido(String texto, int min, int max) {
        return texto.length() >= min && texto.length() <= max;
    }
}
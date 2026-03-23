package fes.jonathan.holamundoimagen.models;

import com.google.gson.annotations.SerializedName;

public class ClienteModel {
    @SerializedName("encodedkey")
    private String encodedKey;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("apellidos")
    private String apellidos;

    @SerializedName("correo")
    private String correo;

    @SerializedName("contrasenia")
    private String contrasenia;

    // Constructor para registro
    public ClienteModel(String encodedKey, String nombre, String apellidos,
                        String correo, String contrasenia) {
        this.encodedKey  = encodedKey;
        this.nombre      = nombre;
        this.apellidos   = apellidos;
        this.correo      = correo;
        this.contrasenia = contrasenia;
    }

    // Getters
    public String getEncodedKey()  { return encodedKey; }
    public String getNombre()      { return nombre; }
    public String getApellidos()   { return apellidos; }
    public String getCorreo()      { return correo; }
    public String getContrasenia() { return contrasenia; }
}
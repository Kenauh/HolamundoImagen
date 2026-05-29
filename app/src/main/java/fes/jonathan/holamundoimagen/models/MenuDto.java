package fes.jonathan.holamundoimagen.models;

import com.google.gson.annotations.SerializedName;

/**
 * Mapea el schema MenuDto del Swagger.
 * Usado en: GET /api/Pizzas/Menus
 *
 * El campo "ruta" contiene la ruta relativa de la imagen del menú
 * (ej. "/images/menus/pizza.png"). Se concatena con BASE_URL para cargarla con Glide.
 */
public class MenuDto {

    @SerializedName("id")
    private int id;

    @SerializedName("titulo")
    private String titulo;

    @SerializedName("subtitulo")
    private String subtitulo;

    @SerializedName("ruta")
    private String ruta;

    public int    getId()       { return id; }
    public String getTitulo()   { return titulo; }
    public String getSubtitulo(){ return subtitulo; }
    public String getRuta()     { return ruta; }
}

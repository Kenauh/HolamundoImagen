package fes.jonathan.holamundoimagen.models;

import com.google.gson.annotations.SerializedName;

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

package fes.jonathan.holamundoimagen.models;

import com.google.gson.annotations.SerializedName;

public class PizzaModel {
    @SerializedName("id")
    private String id;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("descripcion")
    private String descripcion;

    @SerializedName("imagen")
    private String imagen;  // URL de la imagen

    public String getId()          { return id; }
    public String getNombre()      { return nombre; }
    public String getDescripcion() { return descripcion; }
    public String getImagen()      { return imagen; }
}
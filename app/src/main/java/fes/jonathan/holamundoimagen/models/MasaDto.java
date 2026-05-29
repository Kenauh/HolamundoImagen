package fes.jonathan.holamundoimagen.models;

import com.google.gson.annotations.SerializedName;

public class MasaDto {

    @SerializedName("id")
    private int id;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("descripcion")
    private String descripcion;

    public int    getId()          { return id; }
    public String getNombre()      { return nombre; }
    public String getDescripcion() { return descripcion; }
}

package fes.jonathan.holamundoimagen.models;

import com.google.gson.annotations.SerializedName;

public class TamanioDto {

    @SerializedName("id")
    private int id;

    @SerializedName("descripcion")
    private String descripcion;

    @SerializedName("precio")
    private double precio;

    public int    getId()          { return id; }
    public String getDescripcion() { return descripcion; }
    public double getPrecio()      { return precio; }
}

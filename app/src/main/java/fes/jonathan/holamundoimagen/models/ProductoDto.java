package fes.jonathan.holamundoimagen.models;

import com.google.gson.annotations.SerializedName;

public class ProductoDto {

    @SerializedName("id")
    private int id;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("descripcion")
    private String descripcion;

    @SerializedName("ingredientes")
    private String ingredientes;

    @SerializedName("ruta")
    private String ruta;

    @SerializedName("menu")
    private String menu;

    @SerializedName("precio")
    private double precio;

    public int    getId()           { return id; }
    public String getNombre()       { return nombre; }
    public String getDescripcion()  { return descripcion; }
    public String getIngredientes() { return ingredientes; }
    public String getRuta()         { return ruta; }
    public String getMenu()         { return menu; }
    public double getPrecio()       { return precio; }

    public void setId(int id)               { this.id = id; }
    public void setNombre(String nombre)    { this.nombre = nombre; }
    public void setDescripcion(String desc) { this.descripcion = desc; }
    public void setRuta(String ruta)        { this.ruta = ruta; }
    public void setPrecio(double precio)    { this.precio = precio; }
}

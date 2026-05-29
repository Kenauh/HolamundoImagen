package fes.jonathan.holamundoimagen.models;

import com.google.gson.annotations.SerializedName;

/**
 * Mapea el schema ProductoDto del Swagger.
 * Usado para: GET /api/Pizzas/Pizzas, /Pollos, /Adicionales, /Bebidas.
 *
 * CORRECCIONES respecto a la versión anterior:
 *   - id: String → int  (el API devuelve int32)
 *   - imagen → ruta     (nombre real del campo en el JSON)
 */
public class PizzaModel {

    @SerializedName("id")
    private int id;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("descripcion")
    private String descripcion;

    @SerializedName("ingredientes")
    private String ingredientes;

    @SerializedName("ruta")
    private String ruta;   // URL relativa de la imagen del producto

    @SerializedName("menu")
    private String menu;

    @SerializedName("precio")
    private double precio;

    // Getters
    public int    getId()           { return id; }
    public String getNombre()       { return nombre; }
    public String getDescripcion()  { return descripcion; }
    public String getIngredientes() { return ingredientes; }
    public String getRuta()         { return ruta; }
    public String getMenu()         { return menu; }
    public double getPrecio()       { return precio; }
}

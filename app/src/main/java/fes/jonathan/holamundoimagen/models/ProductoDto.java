package fes.jonathan.holamundoimagen.models;

import com.google.gson.annotations.SerializedName;

/**
 * Mapea el schema ProductoDto del Swagger.
 *
 * Reutilizado por los siguientes endpoints:
 *   - GET /api/Pizzas/Pizzas
 *   - GET /api/Pizzas/Pollos
 *   - GET /api/Pizzas/Adicionales
 *   - GET /api/Pizzas/Bebidas
 *   - GET/POST /api/Pizzas/Clientes/Ordenes  (campo "productos" de la orden)
 *
 * El campo "ruta" contiene la URL relativa de la imagen del producto.
 * "menu" y "precio" son readOnly en el Swagger (los calcula el servidor).
 *
 * FASE 3: Se añaden setters completos para poder reconstruir un ProductoDto
 * a partir de los extras planos del Intent en PizzaDetalleActivity,
 * sin necesidad de implementar Parcelable.
 */
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
    private String menu;     // readOnly — categoría del producto

    @SerializedName("precio")
    private double precio;   // readOnly — calculado por el servidor

    // ── Getters ──────────────────────────────────────────────────────────────
    public int    getId()           { return id; }
    public String getNombre()       { return nombre; }
    public String getDescripcion()  { return descripcion; }
    public String getIngredientes() { return ingredientes; }
    public String getRuta()         { return ruta; }
    public String getMenu()         { return menu; }
    public double getPrecio()       { return precio; }

    // ── Setters (Fase 3) ─────────────────────────────────────────────────────
    public void setId(int id)               { this.id = id; }
    public void setNombre(String nombre)    { this.nombre = nombre; }
    public void setDescripcion(String desc) { this.descripcion = desc; }
    public void setRuta(String ruta)        { this.ruta = ruta; }
    public void setPrecio(double precio)    { this.precio = precio; }
}

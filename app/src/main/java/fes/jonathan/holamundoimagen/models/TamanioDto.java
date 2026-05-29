package fes.jonathan.holamundoimagen.models;

import com.google.gson.annotations.SerializedName;

/**
 * Mapea el schema TamanioDto del Swagger.
 * Usado en: GET /api/Pizzas/Pizzas/Tamanios
 *
 * El campo "descripcion" es el identificador string que se envía en OrdenDtoIn
 * (campo "tamanio" de PizzaDtoIn). Ejemplo: "Mediana", "Grande", "Familiar".
 */
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

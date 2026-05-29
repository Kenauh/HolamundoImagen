package fes.jonathan.holamundoimagen.models;

import com.google.gson.annotations.SerializedName;

/**
 * Mapea el schema MasaDto del Swagger.
 * Usado en: GET /api/Pizzas/Pizzas/Masas
 *
 * El campo "id" es el entero que se envía como "masaId" en PizzaDtoIn
 * al momento de armar una orden. Ejemplo: 1 = Delgada, 2 = Gruesa.
 */
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

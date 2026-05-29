package fes.jonathan.holamundoimagen.models;

import com.google.gson.annotations.SerializedName;

/**
 * Mapea el schema PizzaDto del Swagger (lectura).
 * Aparece en el campo "pizzas" de OrdenDto al obtener una orden existente.
 *
 * Permite pizza mitad-y-mitad: pizzaId + pizza2Id (opcional).
 */
public class PizzaDto {

    @SerializedName("pizzaId")
    private int pizzaId;

    @SerializedName("pizza2Id")
    private int pizza2Id;   // 0 si no es mitad-y-mitad

    @SerializedName("nombre1")
    private String nombre1;

    @SerializedName("nombre2")
    private String nombre2; // null si no es mitad-y-mitad

    @SerializedName("tamanio")
    private String tamanio;

    @SerializedName("masa")
    private String masa;

    @SerializedName("precio")
    private double precio;

    public int    getPizzaId()  { return pizzaId; }
    public int    getPizza2Id() { return pizza2Id; }
    public String getNombre1()  { return nombre1; }
    public String getNombre2()  { return nombre2; }
    public String getTamanio()  { return tamanio; }
    public String getMasa()     { return masa; }
    public double getPrecio()   { return precio; }
}

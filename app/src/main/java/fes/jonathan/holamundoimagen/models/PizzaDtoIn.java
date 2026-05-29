package fes.jonathan.holamundoimagen.models;

import com.google.gson.annotations.SerializedName;

/**
 * Mapea el schema PizzaDtoIn del Swagger (escritura).
 * Se envía dentro del array "pizzas" al hacer POST /api/Pizzas/Clientes/Ordenes.
 *
 * Campos requeridos según el Swagger: pizzaId, masaId, tamanio.
 * pizza2Id es opcional (mitad-y-mitad).
 */
public class PizzaDtoIn {

    @SerializedName("pizzaId")
    private int pizzaId;    // required

    @SerializedName("pizza2Id")
    private int pizza2Id;   // opcional — 0 si es pizza entera

    @SerializedName("masaId")
    private int masaId;     // required — id del MasaDto

    @SerializedName("tamanio")
    private String tamanio; // required — descripcion del TamanioDto

    public PizzaDtoIn(int pizzaId, int masaId, String tamanio) {
        this.pizzaId = pizzaId;
        this.masaId  = masaId;
        this.tamanio = tamanio;
    }

    // Constructor para pizza mitad-y-mitad.
    public PizzaDtoIn(int pizzaId, int pizza2Id, int masaId, String tamanio) {
        this.pizzaId  = pizzaId;
        this.pizza2Id = pizza2Id;
        this.masaId   = masaId;
        this.tamanio  = tamanio;
    }

    public int    getPizzaId()  { return pizzaId; }
    public int    getPizza2Id() { return pizza2Id; }
    public int    getMasaId()   { return masaId; }
    public String getTamanio()  { return tamanio; }

    public void setPizza2Id(int pizza2Id) { this.pizza2Id = pizza2Id; }
}

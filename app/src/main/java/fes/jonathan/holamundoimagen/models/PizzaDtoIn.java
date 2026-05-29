package fes.jonathan.holamundoimagen.models;

import com.google.gson.annotations.SerializedName;

public class PizzaDtoIn {

    @SerializedName("pizzaId")
    private int pizzaId;

    @SerializedName("pizza2Id")
    private int pizza2Id;

    @SerializedName("masaId")
    private int masaId;

    @SerializedName("tamanio")
    private String tamanio;

    public PizzaDtoIn(int pizzaId, int masaId, String tamanio) {
        this.pizzaId = pizzaId;
        this.masaId  = masaId;
        this.tamanio = tamanio;
    }

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

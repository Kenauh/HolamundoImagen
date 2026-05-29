package fes.jonathan.holamundoimagen.models;

import com.google.gson.annotations.SerializedName;

public class PizzaDto {

    @SerializedName("pizzaId")
    private int pizzaId;

    @SerializedName("pizza2Id")
    private int pizza2Id;

    @SerializedName("nombre1")
    private String nombre1;

    @SerializedName("nombre2")
    private String nombre2;

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

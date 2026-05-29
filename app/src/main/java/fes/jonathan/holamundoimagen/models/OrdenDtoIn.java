package fes.jonathan.holamundoimagen.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrdenDtoIn {

    @SerializedName("encodedkey")
    private String encodedKey;

    @SerializedName("productos")
    private List<ProductoDtoSimple> productos;

    @SerializedName("pizzas")
    private List<PizzaDtoIn> pizzas;

    @SerializedName("metodoDePago")
    private String metodoDePago;

    public OrdenDtoIn(List<ProductoDtoSimple> productos,
                      List<PizzaDtoIn> pizzas,
                      String metodoDePago) {
        this.productos    = productos;
        this.pizzas       = pizzas;
        this.metodoDePago = metodoDePago;
    }

    public List<ProductoDtoSimple> getProductos()  { return productos; }
    public List<PizzaDtoIn>        getPizzas()     { return pizzas; }
    public String                  getMetodoDePago(){ return metodoDePago; }
}

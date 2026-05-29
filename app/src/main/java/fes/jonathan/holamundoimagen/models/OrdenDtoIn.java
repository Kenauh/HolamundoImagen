package fes.jonathan.holamundoimagen.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Body del POST /api/Pizzas/Clientes/Ordenes.
 *
 * FIX: el campo "productos" ahora usa ProductoDtoSimple (solo { id })
 * en lugar de ProductoDto completo, lo que causaba el error 500.
 * El servidor rechaza campos extra que no reconoce en ese contexto.
 */
public class OrdenDtoIn {

    @SerializedName("encodedkey")
    private String encodedKey;

    @SerializedName("productos")
    private List<ProductoDtoSimple> productos;   // ← solo id, no el DTO completo

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

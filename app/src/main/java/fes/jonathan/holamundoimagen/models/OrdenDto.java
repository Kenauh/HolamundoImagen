package fes.jonathan.holamundoimagen.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Mapea el schema OrdenDto del Swagger (lectura).
 * Respuesta del GET /api/Pizzas/Clientes/Ordenes (endpoint protegido con JWT).
 *
 * Contiene la orden activa del cliente autenticado:
 * los productos adicionales, las pizzas configuradas,
 * el método de pago y el estado actual de la orden.
 */
public class OrdenDto {

    @SerializedName("productos")
    private List<ProductoDto> productos;

    @SerializedName("pizzas")
    private List<PizzaDto> pizzas;

    @SerializedName("metodoDePago")
    private String metodoDePago;

    @SerializedName("estado")
    private String estado;

    public List<ProductoDto> getProductos()   { return productos; }
    public List<PizzaDto>    getPizzas()      { return pizzas; }
    public String            getMetodoDePago(){ return metodoDePago; }
    public String            getEstado()      { return estado; }
}

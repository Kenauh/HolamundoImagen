package fes.jonathan.holamundoimagen.carrito;

import fes.jonathan.holamundoimagen.models.MasaDto;
import fes.jonathan.holamundoimagen.models.ProductoDto;
import fes.jonathan.holamundoimagen.models.TamanioDto;

public class CarritoItem {

    public enum Tipo { PIZZA, PRODUCTO }

    private final Tipo        tipo;
    private final ProductoDto producto;
    private final ProductoDto pizza2;
    private final TamanioDto  tamanio;
    private final MasaDto     masa;
    private final double      subtotal;

    public CarritoItem(ProductoDto pizza, ProductoDto pizza2, TamanioDto tamanio, MasaDto masa) {
        this.tipo     = Tipo.PIZZA;
        this.producto = pizza;
        this.pizza2   = pizza2;
        this.tamanio  = tamanio;
        this.masa     = masa;
        this.subtotal = tamanio != null ? tamanio.getPrecio() : 0;
    }

    public CarritoItem(ProductoDto producto) {
        this.tipo     = Tipo.PRODUCTO;
        this.producto = producto;
        this.pizza2   = null;
        this.tamanio  = null;
        this.masa     = null;
        this.subtotal = producto.getPrecio();
    }

    public Tipo        getTipo()     { return tipo; }
    public ProductoDto getProducto() { return producto; }
    public ProductoDto getPizza2()   { return pizza2; }
    public TamanioDto  getTamanio()  { return tamanio; }
    public MasaDto     getMasa()     { return masa; }
    public double      getSubtotal() { return subtotal; }

    // Etiqueta legible para el carrito: "Pepperoni · Grande · Delgada" o "Pollo BBQ"
    public String getEtiqueta() {
        if (tipo == Tipo.PRODUCTO) {
            return producto.getNombre() != null ? producto.getNombre() : "";
        }
        StringBuilder sb = new StringBuilder();
        if (producto != null && producto.getNombre() != null) sb.append(producto.getNombre());
        if (pizza2   != null && pizza2.getNombre()   != null) sb.append(" / ").append(pizza2.getNombre());
        if (tamanio  != null && tamanio.getDescripcion() != null) sb.append(" · ").append(tamanio.getDescripcion());
        if (masa     != null && masa.getNombre()      != null) sb.append(" · ").append(masa.getNombre());
        return sb.toString();
    }
}

package fes.jonathan.holamundoimagen.carrito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fes.jonathan.holamundoimagen.models.MasaDto;
import fes.jonathan.holamundoimagen.models.ProductoDto;
import fes.jonathan.holamundoimagen.models.TamanioDto;

// Singleton en memoria que acumula los ítems del carrito durante la sesión de compra.
public class CarritoManager {

    private static CarritoManager instance;
    private final List<CarritoItem> items = new ArrayList<>();

    private CarritoManager() {}

    public static synchronized CarritoManager getInstance() {
        if (instance == null) instance = new CarritoManager();
        return instance;
    }

    public void agregarPizza(ProductoDto pizza, ProductoDto pizza2,
                             TamanioDto tamanio, MasaDto masa) {
        items.add(new CarritoItem(pizza, pizza2, tamanio, masa));
    }

    public void agregarProducto(ProductoDto producto) {
        items.add(new CarritoItem(producto));
    }

    public List<CarritoItem> getItems()    { return Collections.unmodifiableList(items); }
    public int               getCantidad() { return items.size(); }
    public boolean           estaVacio()   { return items.isEmpty(); }

    public double getTotal() {
        double total = 0;
        for (CarritoItem item : items) total += item.getSubtotal();
        return total;
    }

    public void eliminarItem(int posicion) { items.remove(posicion); }
    public void vaciar()                   { items.clear(); }
}

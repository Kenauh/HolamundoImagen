package fes.jonathan.holamundoimagen.models;

import com.google.gson.annotations.SerializedName;

/**
 * DTO mínimo para enviar un producto en el body de POST /Clientes/Ordenes.
 *
 * El servidor rechaza con 500 si se envían campos extra (ruta, descripcion, etc.)
 * porque el schema ProductoDto en escritura solo espera { "id": int }.
 * Esta clase reemplaza a ProductoDto en el array "productos" de OrdenDtoIn.
 */
public class ProductoDtoSimple {

    @SerializedName("id")
    private int id;

    public ProductoDtoSimple(int id) {
        this.id = id;
    }

    public int getId() { return id; }
}

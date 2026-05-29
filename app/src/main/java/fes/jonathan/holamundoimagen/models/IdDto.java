package fes.jonathan.holamundoimagen.models;

import com.google.gson.annotations.SerializedName;

/**
 * Mapea el schema IdDto del Swagger.
 * "Represents a data transfer object containing an identifier, a message, and a timestamp."
 *
 * Usado en las respuestas de:
 *   - POST /api/Pizzas/Clientes       (registro — HTTP 200 y 201)
 *   - POST /api/Pizzas/Clientes/Ordenes (crear orden — HTTP 201)
 */
public class IdDto {

    @SerializedName("id")
    private String id;

    @SerializedName("mensaje")
    private String mensaje;

    @SerializedName("fecha")
    private String fecha;   // ISO 8601 date-time

    public String getId()     { return id; }
    public String getMensaje(){ return mensaje; }
    public String getFecha()  { return fecha; }
}

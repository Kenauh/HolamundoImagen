package fes.jonathan.holamundoimagen.models;

import com.google.gson.annotations.SerializedName;

public class IdDto {

    @SerializedName("id")
    private String id;

    @SerializedName("mensaje")
    private String mensaje;

    @SerializedName("fecha")
    private String fecha;

    public String getId()     { return id; }
    public String getMensaje(){ return mensaje; }
    public String getFecha()  { return fecha; }
}

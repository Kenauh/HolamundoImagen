package fes.jonathan.holamundoimagen.models;

import com.google.gson.annotations.SerializedName;

public class SesionResponse {
    @SerializedName("token")
    private String token;

    @SerializedName("expiracion")
    private String expiracion;

    public String getToken()      { return token; }
    public String getExpiracion() { return expiracion; }
}
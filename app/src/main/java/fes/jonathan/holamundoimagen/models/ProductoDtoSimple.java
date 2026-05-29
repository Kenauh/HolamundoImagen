package fes.jonathan.holamundoimagen.models;

import com.google.gson.annotations.SerializedName;

public class ProductoDtoSimple {

    @SerializedName("id")
    private int id;

    public ProductoDtoSimple(int id) {
        this.id = id;
    }

    public int getId() { return id; }
}

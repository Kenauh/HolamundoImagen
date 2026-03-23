package fes.jonathan.holamundoimagen;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "https://utilidades.vmartinez84.xyz/";
    private static Retrofit retrofit;

    private String Token;

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public static Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    // Shortcut para obtener el servicio directamente
    public static fes.jonathan.holamundoimagen.services.PizzasService getService() {
        return getInstance().create(
                fes.jonathan.holamundoimagen.services.PizzasService.class
        );
    }

}
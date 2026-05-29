package fes.jonathan.holamundoimagen;

import android.content.Context;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import fes.jonathan.holamundoimagen.services.PizzasService;

public class ApiClient {

    private static final String BASE_URL = "https://utilidades.vmartinez84.xyz/";
    private static Retrofit retrofit;

    private ApiClient() {}

    // Debe llamarse una sola vez en PizzasApp.onCreate()
    public static void init(Context context) {
        if (retrofit != null) return;

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor(context))
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Retrofit getInstance() {
        if (retrofit == null) {
            throw new IllegalStateException(
                "ApiClient no inicializado. Llama a ApiClient.init(context) en PizzasApp.onCreate()."
            );
        }
        return retrofit;
    }

    public static PizzasService getService() {
        return getInstance().create(PizzasService.class);
    }
}

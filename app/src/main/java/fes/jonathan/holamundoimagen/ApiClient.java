package fes.jonathan.holamundoimagen;

import android.content.Context;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import fes.jonathan.holamundoimagen.services.PizzasService;

/**
 * Singleton de Retrofit configurado con:
 *   - AuthInterceptor: inyecta el Bearer token en cada petición autenticada.
 *   - GsonConverterFactory: deserializa las respuestas JSON a los DTOs del proyecto.
 *
 * USO:
 *   1. Llamar a ApiClient.init(context) UNA sola vez desde PizzasApp.onCreate().
 *   2. Obtener el servicio con ApiClient.getService() desde Repository o Activity.
 *
 * IMPORTANTE: getService() lanza IllegalStateException si se llama antes de init().
 */
public class ApiClient {

    private static final String BASE_URL = "https://utilidades.vmartinez84.xyz/";

    private static Retrofit retrofit;

    // Constructor privado — esta clase solo se usa de forma estática.
    private ApiClient() {}

    /**
     * Inicializa el cliente Retrofit con el OkHttpClient+AuthInterceptor.
     * Debe llamarse exactamente una vez en PizzasApp.onCreate().
     *
     * @param context Contexto de aplicación (Application.getApplicationContext()).
     */
    public static void init(Context context) {
        if (retrofit != null) {
            return; // Ya inicializado, no hacer nada.
        }

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor(context))
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * Devuelve la instancia de Retrofit. Requiere haber llamado init() antes.
     */
    public static Retrofit getInstance() {
        if (retrofit == null) {
            throw new IllegalStateException(
                "ApiClient no inicializado. Llama a ApiClient.init(context) en PizzasApp.onCreate()."
            );
        }
        return retrofit;
    }

    /**
     * Atajo para obtener el PizzasService directamente.
     */
    public static PizzasService getService() {
        return getInstance().create(PizzasService.class);
    }
}

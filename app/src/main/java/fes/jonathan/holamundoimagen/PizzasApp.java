package fes.jonathan.holamundoimagen;

import android.app.Application;

/**
 * Clase Application del proyecto.
 * Punto de entrada único para inicializar dependencias globales.
 * Declarada en AndroidManifest con android:name=".PizzasApp".
 */
public class PizzasApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Inicializa Retrofit con el AuthInterceptor antes de cualquier llamada de red.
        ApiClient.init(this);
    }
}

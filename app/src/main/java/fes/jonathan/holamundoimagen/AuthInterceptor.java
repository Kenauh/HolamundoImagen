package fes.jonathan.holamundoimagen;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Interceptor de OkHttp que adjunta el token JWT a todas las peticiones salientes.
 *
 * Flujo:
 *   1. Lee el token del SesionManager en tiempo de ejecución (no al construir).
 *   2. Si hay token, agrega el header "Authorization: Bearer {token}".
 *   3. Si no hay token (usuario no autenticado), la request pasa sin modificar.
 *      Los endpoints públicos (Menus, Pizzas, Clientes) no requieren token.
 */
public class AuthInterceptor implements Interceptor {

    private final SesionManager sesionManager;

    public AuthInterceptor(Context context) {
        // Usamos el applicationContext para evitar memory leaks.
        this.sesionManager = new SesionManager(context.getApplicationContext());
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        String token = sesionManager.getToken();
        Request originalRequest = chain.request();

        if (token == null) {
            // Sin sesión activa: endpoint público, no se modifica la request.
            return chain.proceed(originalRequest);
        }

        // Con sesión activa: inyecta el header Bearer.
        Request authenticatedRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer " + token)
                .build();

        return chain.proceed(authenticatedRequest);
    }
}

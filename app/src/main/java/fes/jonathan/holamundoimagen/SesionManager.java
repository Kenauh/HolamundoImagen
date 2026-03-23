package fes.jonathan.holamundoimagen;

import android.content.Context;
import android.content.SharedPreferences;

public class SesionManager {
    private static final String PREF_NAME      = "session";
    private static final String KEY_TOKEN      = "token";
    private static final String KEY_EXPIRACION = "expiracion";
    private static final String KEY_CORREO     = "correo";

    private final SharedPreferences prefs;

    public SesionManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void guardarSesion(String token, String expiracion, String correo) {
        prefs.edit()
                .putString(KEY_TOKEN, token)
                .putString(KEY_EXPIRACION, expiracion)
                .putString(KEY_CORREO, correo)
                .apply();
    }

    public String getToken()      { return prefs.getString(KEY_TOKEN, null); }
    public String getExpiracion() { return prefs.getString(KEY_EXPIRACION, null); }
    public String getCorreo()     { return prefs.getString(KEY_CORREO, null); }

    public boolean haySesion()    { return getToken() != null; }

    public void cerrarSesion()    { prefs.edit().clear().apply(); }
}

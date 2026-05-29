package fes.jonathan.holamundoimagen;

import android.app.Application;

public class PizzasApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ApiClient.init(this);
    }
}

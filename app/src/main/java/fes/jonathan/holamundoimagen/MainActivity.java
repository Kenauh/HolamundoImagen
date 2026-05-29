package fes.jonathan.holamundoimagen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import fes.jonathan.holamundoimagen.carrito.CarritoManager;

public class MainActivity extends AppCompatActivity {

    private static final String BASE = "https://utilidades.vmartinez84.xyz";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton btnPizzas   = findViewById(R.id.imageButton);
        ImageButton btnEntradas = findViewById(R.id.imageButton2);
        ImageButton btnPollo    = findViewById(R.id.imageButton3);
        ImageButton btnPostres  = findViewById(R.id.imageButton4);
        ImageButton btnBebidas  = findViewById(R.id.imageButton5);

        Glide.with(this).load(BASE + "/images/menus/pizza.png")   .into(btnPizzas);
        Glide.with(this).load(BASE + "/images/menus/breads.png")  .into(btnEntradas);
        Glide.with(this).load(BASE + "/images/menus/chicken.png") .into(btnPollo);
        Glide.with(this).load(BASE + "/images/menus/dessert.png") .into(btnPostres);
        Glide.with(this).load(BASE + "/images/menus/drinks.png")  .into(btnBebidas);

        actualizarBadgeCarrito();
    }

    @Override
    protected void onResume() {
        super.onResume();
        actualizarBadgeCarrito();
    }

    public void irAlCarrito(View view) {
        startActivity(new Intent(this, CarritoActivity.class));
    }

    public void irAlHistorial(View view) {
        startActivity(new Intent(this, HistorialActivity.class));
    }

    public void irAPizzas(View view)  { abrirCatalogo(CatalogoActivity.CAT_PIZZAS); }
    public void Entradas(View view)   { abrirCatalogo(CatalogoActivity.CAT_ADICIONALES); }
    public void Pollo(View view)      { abrirCatalogo(CatalogoActivity.CAT_POLLOS); }
    public void Postres(View view)    { abrirCatalogo(CatalogoActivity.CAT_ADICIONALES); }
    public void Bebidas(View view)    { abrirCatalogo(CatalogoActivity.CAT_BEBIDAS); }

    public void irALogin(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void cerrarSesion(View view) {
        new SesionManager(this).cerrarSesion();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void abrirCatalogo(String categoria) {
        Intent intent = new Intent(this, CatalogoActivity.class);
        intent.putExtra(CatalogoActivity.EXTRA_CATEGORIA, categoria);
        startActivity(intent);
    }

    private void actualizarBadgeCarrito() {
        TextView badge = findViewById(R.id.badgeCarrito);
        if (badge == null) return;
        int cantidad = CarritoManager.getInstance().getItems().size();
        if (cantidad > 0) {
            badge.setVisibility(View.VISIBLE);
            badge.setText(String.valueOf(cantidad));
        } else {
            badge.setVisibility(View.GONE);
        }
    }
}

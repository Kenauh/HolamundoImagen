package fes.jonathan.holamundoimagen;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

import fes.jonathan.holamundoimagen.models.OrdenDto;
import fes.jonathan.holamundoimagen.models.PizzaDto;
import fes.jonathan.holamundoimagen.models.ProductoDto;
import fes.jonathan.holamundoimagen.repository.PizzaRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistorialActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView    tvContenido;
    private TextView    tvEstado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Mi Pedido");
        }

        progressBar = findViewById(R.id.progressBar);
        tvContenido = findViewById(R.id.tvContenido);
        tvEstado    = findViewById(R.id.tvEstado);

        cargarOrden();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) { onBackPressed(); return true; }
        return super.onOptionsItemSelected(item);
    }

    private void cargarOrden() {
        progressBar.setVisibility(View.VISIBLE);
        tvContenido.setText("");
        tvEstado.setText("");

        new PizzaRepository().obtenerOrden().enqueue(new Callback<OrdenDto>() {
            @Override
            public void onResponse(@NonNull Call<OrdenDto> call,
                                   @NonNull Response<OrdenDto> response) {
                progressBar.setVisibility(View.GONE);

                if (response.code() == 401) {
                    tvEstado.setText("⚠ Debes iniciar sesión para ver tu historial.");
                    return;
                }

                if (!response.isSuccessful() || response.body() == null) {
                    tvEstado.setText("No se encontró ninguna orden registrada.");
                    return;
                }

                mostrarOrden(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<OrdenDto> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                Snackbar.make(tvContenido,
                        "Sin conexión. Verifica tu internet.", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void mostrarOrden(OrdenDto orden) {
        StringBuilder sb = new StringBuilder();

        String estado = orden.getEstado() != null ? orden.getEstado() : "En proceso";
        String metodo = orden.getMetodoDePago() != null ? orden.getMetodoDePago() : "—";
        tvEstado.setText("Estado: " + estado + "   |   Pago: " + metodo);

        if (orden.getPizzas() != null && !orden.getPizzas().isEmpty()) {
            sb.append("🍕 Pizzas\n─────────────────────────\n");
            for (PizzaDto pizza : orden.getPizzas()) {
                String nombre = pizza.getNombre1() != null ? pizza.getNombre1() : "Pizza";
                if (pizza.getNombre2() != null && !pizza.getNombre2().isEmpty()) {
                    nombre += " / " + pizza.getNombre2();
                }
                String tam  = pizza.getTamanio() != null ? pizza.getTamanio() : "";
                String masa = pizza.getMasa()    != null ? pizza.getMasa()    : "";
                sb.append("• ").append(nombre)
                  .append("  (").append(tam).append(" — ").append(masa).append(")\n")
                  .append("  $").append(String.format("%.2f", pizza.getPrecio())).append("\n\n");
            }
        }

        if (orden.getProductos() != null && !orden.getProductos().isEmpty()) {
            sb.append("🛒 Productos\n─────────────────────────\n");
            for (ProductoDto p : orden.getProductos()) {
                sb.append("• ").append(p.getNombre() != null ? p.getNombre() : "Producto").append("\n");
            }
        }

        tvContenido.setText(sb.length() == 0 ? "La orden está vacía." : sb.toString());
    }
}

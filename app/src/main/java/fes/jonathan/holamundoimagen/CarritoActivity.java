package fes.jonathan.holamundoimagen;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import fes.jonathan.holamundoimagen.adapter.CarritoAdapter;
import fes.jonathan.holamundoimagen.carrito.CarritoItem;
import fes.jonathan.holamundoimagen.carrito.CarritoManager;
import fes.jonathan.holamundoimagen.models.IdDto;
import fes.jonathan.holamundoimagen.models.OrdenDtoIn;
import fes.jonathan.holamundoimagen.models.PizzaDtoIn;
import fes.jonathan.holamundoimagen.models.ProductoDtoSimple;
import fes.jonathan.holamundoimagen.repository.PizzaRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarritoActivity extends AppCompatActivity {

    private RecyclerView   rvCarrito;
    private CarritoAdapter adapter;
    private TextView       tvTotal;
    private TextView       tvVacio;
    private Button         btnConfirmar;
    private Button         btnCancelar;
    private Spinner        spinnerMetodoPago;
    private ProgressBar    progressBar;
    private View           rootView;

    private final PizzaRepository repository = new PizzaRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Mi carrito");
        }

        rootView          = findViewById(R.id.rootCarrito);
        rvCarrito         = findViewById(R.id.rvCarrito);
        tvTotal           = findViewById(R.id.tvTotal);
        tvVacio           = findViewById(R.id.tvVacio);
        btnConfirmar      = findViewById(R.id.btnConfirmar);
        btnCancelar       = findViewById(R.id.btnCancelar);
        spinnerMetodoPago = findViewById(R.id.spinnerMetodoPago);
        progressBar       = findViewById(R.id.progressCarrito);

        spinnerMetodoPago.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                new String[]{"Efectivo", "Tarjeta"}));

        adapter = new CarritoAdapter(posicion -> {
            CarritoManager.getInstance().eliminarItem(posicion);
            refrescarUI();
        });

        rvCarrito.setLayoutManager(new LinearLayoutManager(this));
        rvCarrito.setAdapter(adapter);

        btnConfirmar.setOnClickListener(v -> confirmarPedido());
        btnCancelar.setOnClickListener(v -> mostrarDialogoCancelar());

        refrescarUI();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) { onBackPressed(); return true; }
        return super.onOptionsItemSelected(item);
    }

    private void refrescarUI() {
        List<CarritoItem> items = CarritoManager.getInstance().getItems();
        adapter.submitList(new ArrayList<>(items));

        boolean vacio = CarritoManager.getInstance().estaVacio();
        tvVacio.setVisibility(vacio ? View.VISIBLE : View.GONE);
        rvCarrito.setVisibility(vacio ? View.GONE : View.VISIBLE);
        btnConfirmar.setEnabled(!vacio);
        btnCancelar.setEnabled(!vacio);
        spinnerMetodoPago.setEnabled(!vacio);
        tvTotal.setText(String.format("Total: $%.0f", CarritoManager.getInstance().getTotal()));
    }

    private void mostrarDialogoCancelar() {
        new AlertDialog.Builder(this)
                .setTitle("Cancelar pedido")
                .setMessage("¿Deseas vaciar el carrito y cancelar el pedido?")
                .setPositiveButton("Sí, cancelar", (d, w) -> {
                    CarritoManager.getInstance().vaciar();
                    refrescarUI();
                    Snackbar.make(rootView, "Carrito vaciado.", Snackbar.LENGTH_SHORT).show();
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void confirmarPedido() {
        if (!new SesionManager(this).haySesion()) {
            Snackbar.make(rootView, "Inicia sesión para confirmar tu pedido.", Snackbar.LENGTH_LONG)
                    .setAction("Iniciar sesión", v ->
                            startActivity(new Intent(this, LoginActivity.class)))
                    .show();
            return;
        }

        List<PizzaDtoIn>        pizzasOut    = new ArrayList<>();
        List<ProductoDtoSimple> productosOut = new ArrayList<>();

        for (CarritoItem item : CarritoManager.getInstance().getItems()) {
            if (item.getTipo() == CarritoItem.Tipo.PIZZA) {
                PizzaDtoIn p = new PizzaDtoIn(
                        item.getProducto().getId(),
                        item.getMasa()    != null ? item.getMasa().getId()             : 1,
                        item.getTamanio() != null ? item.getTamanio().getDescripcion() : ""
                );
                if (item.getPizza2() != null) p.setPizza2Id(item.getPizza2().getId());
                pizzasOut.add(p);
            } else {
                productosOut.add(new ProductoDtoSimple(item.getProducto().getId()));
            }
        }

        String metodo = spinnerMetodoPago.getSelectedItem() != null
                ? spinnerMetodoPago.getSelectedItem().toString()
                : "Efectivo";

        OrdenDtoIn orden = new OrdenDtoIn(productosOut, pizzasOut, metodo);

        setFormHabilitado(false);
        repository.crearOrden(orden).enqueue(new Callback<IdDto>() {
            @Override
            public void onResponse(@NonNull Call<IdDto> call,
                                   @NonNull Response<IdDto> response) {
                setFormHabilitado(true);
                if (response.isSuccessful() && response.body() != null) {
                    String idOrden = response.body().getId();
                    CarritoManager.getInstance().vaciar();
                    refrescarUI();
                    new AlertDialog.Builder(CarritoActivity.this)
                            .setTitle("¡Pedido confirmado! 🎉")
                            .setMessage("Tu pedido fue registrado.\nID: " + idOrden)
                            .setPositiveButton("Ver mi pedido", (d, w) ->
                                    startActivity(new Intent(CarritoActivity.this,
                                            HistorialActivity.class)))
                            .setNegativeButton("Cerrar", null)
                            .show();
                } else if (response.code() == 401) {
                    Snackbar.make(rootView, "Sesión expirada. Inicia sesión de nuevo.",
                            Snackbar.LENGTH_LONG)
                            .setAction("Login", v ->
                                    startActivity(new Intent(CarritoActivity.this,
                                            LoginActivity.class)))
                            .show();
                } else {
                    Snackbar.make(rootView,
                            "Error " + response.code() + ". Verifica tu conexión e intenta de nuevo.",
                            Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<IdDto> call, @NonNull Throwable t) {
                setFormHabilitado(true);
                Snackbar.make(rootView, "Sin conexión. Verifica tu internet.",
                        Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void setFormHabilitado(boolean habilitado) {
        btnConfirmar.setEnabled(habilitado);
        btnCancelar.setEnabled(habilitado);
        spinnerMetodoPago.setEnabled(habilitado);
        progressBar.setVisibility(habilitado ? View.GONE : View.VISIBLE);
    }
}

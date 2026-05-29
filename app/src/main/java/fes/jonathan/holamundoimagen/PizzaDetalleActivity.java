package fes.jonathan.holamundoimagen;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import fes.jonathan.holamundoimagen.carrito.CarritoManager;
import fes.jonathan.holamundoimagen.models.MasaDto;
import fes.jonathan.holamundoimagen.models.ProductoDto;
import fes.jonathan.holamundoimagen.models.TamanioDto;
import fes.jonathan.holamundoimagen.viewmodel.PizzasViewModel;

/**
 * Detalle de pizza — Fase 4 corregida.
 *
 * Correcciones:
 *  - Toolbar azul con setSupportActionBar + setDisplayHomeAsUpEnabled(true)
 *  - URL de imagen construida correctamente (BASE sin slash final)
 */
public class PizzaDetalleActivity extends AppCompatActivity {

    private static final String BASE_IMG_URL = "https://utilidades.vmartinez84.xyz";

    private ImageView   imgDetalle;
    private TextView    tvNombreDetalle;
    private TextView    tvDescDetalle;
    private Spinner     spinnerTamanio;
    private Spinner     spinnerMasa;
    private Button      btnAgregarCarrito;
    private ProgressBar progressBar;
    private View        rootView;

    private PizzasViewModel  viewModel;
    private List<TamanioDto> tamanios = new ArrayList<>();
    private List<MasaDto>    masas    = new ArrayList<>();
    private ProductoDto      pizzaActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_detalle);

        // ── Toolbar azul con back ─────────────────────────────────────────
        Toolbar toolbar = findViewById(R.id.toolbarDetalle);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // ── Vistas ────────────────────────────────────────────────────────
        rootView          = findViewById(R.id.rootDetalle);
        imgDetalle        = findViewById(R.id.imgDetalle);
        tvNombreDetalle   = findViewById(R.id.tvNombreDetalle);
        tvDescDetalle     = findViewById(R.id.tvDescDetalle);
        spinnerTamanio    = findViewById(R.id.spinnerTamanio);
        spinnerMasa       = findViewById(R.id.spinnerMasa);
        btnAgregarCarrito = findViewById(R.id.btnAgregarCarrito);
        progressBar       = findViewById(R.id.progressDetalle);

        // ── Datos del Intent ──────────────────────────────────────────────
        int    id     = getIntent().getIntExtra("id", 0);
        String nombre = getIntent().getStringExtra("nombre");
        String desc   = getIntent().getStringExtra("descripcion");
        String ingred = getIntent().getStringExtra("ingredientes");
        String ruta   = getIntent().getStringExtra("ruta");

        pizzaActual = new ProductoDto();
        pizzaActual.setId(id);
        pizzaActual.setNombre(nombre);
        pizzaActual.setDescripcion(desc);
        pizzaActual.setRuta(ruta);

        // ── Poblar vistas estáticas ───────────────────────────────────────
        if (getSupportActionBar() != null) getSupportActionBar().setTitle(nombre != null ? nombre : "Detalle");
        tvNombreDetalle.setText(nombre != null ? nombre : "");

        // Descripción o ingredientes
        String detalle = "";
        if (ingred != null && !ingred.isEmpty()) detalle = ingred;
        else if (desc != null && !desc.isEmpty()) detalle = desc;
        tvDescDetalle.setText(detalle);

        // ── Imagen con URL correcta ───────────────────────────────────────
        if (ruta != null && !ruta.isEmpty()) {
            String imgUrl;
            if (ruta.startsWith("http")) {
                imgUrl = ruta;
            } else {
                // ruta puede venir como "/images/pizzas/xxx.png" o "images/pizzas/xxx.png"
                imgUrl = BASE_IMG_URL + (ruta.startsWith("/") ? ruta : "/" + ruta);
            }
            Glide.with(this)
                    .load(imgUrl)
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .error(android.R.drawable.ic_menu_gallery)
                    .centerCrop()
                    .into(imgDetalle);
        }

        // ── ViewModel ─────────────────────────────────────────────────────
        viewModel = new ViewModelProvider(this).get(PizzasViewModel.class);

        viewModel.isLoading().observe(this, loading ->
                progressBar.setVisibility(loading ? View.VISIBLE : View.GONE));

        viewModel.getTamanios().observe(this, lista -> {
            tamanios = lista;
            poblarSpinnerTamanios(lista);
        });

        viewModel.getMasas().observe(this, lista -> {
            masas = lista;
            poblarSpinnerMasas(lista);
        });

        viewModel.getError().observe(this, msg -> {
            if (msg != null && !msg.isEmpty())
                Snackbar.make(rootView, msg, Snackbar.LENGTH_LONG).show();
        });

        viewModel.cargarOpcionesDePizza();

        btnAgregarCarrito.setOnClickListener(v -> agregarAlCarrito());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) { onBackPressed(); return true; }
        return super.onOptionsItemSelected(item);
    }

    // ── Spinners ──────────────────────────────────────────────────────────

    private void poblarSpinnerTamanios(List<TamanioDto> lista) {
        List<String> opciones = new ArrayList<>();
        for (TamanioDto t : lista) {
            opciones.add(t.getDescripcion() + "  —  $" + String.format("%.0f", t.getPrecio()));
        }
        spinnerTamanio.setAdapter(new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, opciones));
    }

    private void poblarSpinnerMasas(List<MasaDto> lista) {
        List<String> opciones = new ArrayList<>();
        for (MasaDto m : lista) {
            opciones.add(m.getNombre() != null ? m.getNombre() : "Masa " + m.getId());
        }
        spinnerMasa.setAdapter(new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, opciones));
    }

    // ── Carrito ───────────────────────────────────────────────────────────

    private void agregarAlCarrito() {
        if (tamanios.isEmpty() || masas.isEmpty()) {
            Snackbar.make(rootView, "Espera a que carguen los tamaños y masas.",
                    Snackbar.LENGTH_SHORT).show();
            return;
        }
        TamanioDto tam  = tamanios.get(spinnerTamanio.getSelectedItemPosition());
        MasaDto    masa = masas.get(spinnerMasa.getSelectedItemPosition());

        CarritoManager.getInstance().agregarPizza(pizzaActual, null, tam, masa);

        Snackbar.make(rootView,
                "¡" + pizzaActual.getNombre() + " agregada! 🍕",
                Snackbar.LENGTH_LONG)
                .setAction("Ver carrito", v ->
                        startActivity(new android.content.Intent(this, CarritoActivity.class)))
                .show();
    }
}

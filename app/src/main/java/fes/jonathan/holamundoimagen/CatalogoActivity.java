package fes.jonathan.holamundoimagen;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import fes.jonathan.holamundoimagen.adapter.ProductoAdapter;
import fes.jonathan.holamundoimagen.carrito.CarritoManager;
import fes.jonathan.holamundoimagen.models.ProductoDto;
import fes.jonathan.holamundoimagen.viewmodel.PizzasViewModel;

public class CatalogoActivity extends AppCompatActivity {

    public static final String EXTRA_CATEGORIA  = "categoria";
    public static final String CAT_PIZZAS       = "pizzas";
    public static final String CAT_POLLOS       = "pollos";
    public static final String CAT_BEBIDAS      = "bebidas";
    public static final String CAT_ADICIONALES  = "adicionales";

    private PizzasViewModel viewModel;
    private ProductoAdapter adapter;
    private ProgressBar     progressBar;
    private RecyclerView    rvProductos;
    private String          categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);

        categoria = getIntent().getStringExtra(EXTRA_CATEGORIA);
        if (categoria == null) categoria = CAT_PIZZAS;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(tituloDe(categoria));
        }

        progressBar = findViewById(R.id.progressBar);
        rvProductos = findViewById(R.id.rvProductos);

        adapter = new ProductoAdapter(this::onProductoClick);
        rvProductos.setLayoutManager(new GridLayoutManager(this, 2));
        rvProductos.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(PizzasViewModel.class);

        viewModel.isLoading().observe(this, loading ->
                progressBar.setVisibility(loading ? View.VISIBLE : View.GONE));

        viewModel.getError().observe(this, msg -> {
            if (msg != null && !msg.isEmpty())
                Snackbar.make(rvProductos, msg, Snackbar.LENGTH_LONG).show();
        });

        cargarCategoria();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) { onBackPressed(); return true; }
        return super.onOptionsItemSelected(item);
    }

    private void cargarCategoria() {
        switch (categoria) {
            case CAT_PIZZAS:
                viewModel.getPizzas().observe(this, p -> adapter.submitList(p));
                viewModel.cargarPizzas();
                break;
            case CAT_POLLOS:
                viewModel.getPollos().observe(this, p -> adapter.submitList(p));
                viewModel.cargarPollos();
                break;
            case CAT_BEBIDAS:
                viewModel.getBebidas().observe(this, p -> adapter.submitList(p));
                viewModel.cargarBebidas();
                break;
            case CAT_ADICIONALES:
            default:
                viewModel.getAdicionales().observe(this, p -> adapter.submitList(p));
                viewModel.cargarAdicionales();
                break;
        }
    }

    private void onProductoClick(ProductoDto producto) {
        if (CAT_PIZZAS.equals(categoria)) {
            Intent intent = new Intent(this, PizzaDetalleActivity.class);
            intent.putExtra("id",           producto.getId());
            intent.putExtra("nombre",       producto.getNombre());
            intent.putExtra("descripcion",  producto.getDescripcion());
            intent.putExtra("ingredientes", producto.getIngredientes());
            intent.putExtra("ruta",         producto.getRuta());
            startActivity(intent);
        } else {
            CarritoManager.getInstance().agregarProducto(producto);
            Snackbar.make(rvProductos,
                    "\"" + producto.getNombre() + "\" agregado al carrito",
                    Snackbar.LENGTH_SHORT)
                    .setAction("Ver carrito", v ->
                            startActivity(new Intent(this, CarritoActivity.class)))
                    .show();
        }
    }

    private String tituloDe(String cat) {
        switch (cat) {
            case CAT_PIZZAS:      return "Pizzas";
            case CAT_POLLOS:      return "Pollo";
            case CAT_BEBIDAS:     return "Bebidas";
            case CAT_ADICIONALES: return "Adicionales";
            default:              return "Menú";
        }
    }
}

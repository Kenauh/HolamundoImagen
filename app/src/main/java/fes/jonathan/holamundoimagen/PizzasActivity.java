package fes.jonathan.holamundoimagen;

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

import fes.jonathan.holamundoimagen.adapter.PizzaAdapter;
import fes.jonathan.holamundoimagen.viewmodel.PizzasViewModel;

public class PizzasActivity extends AppCompatActivity {

    private PizzasViewModel viewModel;
    private PizzaAdapter    adapter;
    private ProgressBar     progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizzas);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Pizzas");
        }

        progressBar = findViewById(R.id.progressBar);
        RecyclerView rvPizzas = findViewById(R.id.rvPizzas);

        adapter = new PizzaAdapter();
        rvPizzas.setLayoutManager(new GridLayoutManager(this, 2));
        rvPizzas.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(PizzasViewModel.class);

        viewModel.isLoading().observe(this, loading ->
                progressBar.setVisibility(loading ? View.VISIBLE : View.GONE));

        viewModel.getPizzas().observe(this, pizzas -> adapter.submitList(pizzas));

        viewModel.getError().observe(this, msg -> {
            if (msg != null && !msg.isEmpty())
                Snackbar.make(rvPizzas, msg, Snackbar.LENGTH_LONG).show();
        });

        viewModel.cargarPizzas();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) { onBackPressed(); return true; }
        return super.onOptionsItemSelected(item);
    }
}

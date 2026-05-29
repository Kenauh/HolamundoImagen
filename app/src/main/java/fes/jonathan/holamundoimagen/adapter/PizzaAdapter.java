package fes.jonathan.holamundoimagen.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import fes.jonathan.holamundoimagen.PizzaDetalleActivity;
import fes.jonathan.holamundoimagen.R;
import fes.jonathan.holamundoimagen.models.ProductoDto;

public class PizzaAdapter extends ListAdapter<ProductoDto, PizzaAdapter.PizzaViewHolder> {

    private static final String BASE_IMG_URL = "https://utilidades.vmartinez84.xyz";

    private static final DiffUtil.ItemCallback<ProductoDto> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<ProductoDto>() {
                @Override
                public boolean areItemsTheSame(@NonNull ProductoDto a, @NonNull ProductoDto b) {
                    return a.getId() == b.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull ProductoDto a, @NonNull ProductoDto b) {
                    return a.getId() == b.getId()
                            && strEquals(a.getNombre(), b.getNombre())
                            && strEquals(a.getRuta(),   b.getRuta())
                            && Double.compare(a.getPrecio(), b.getPrecio()) == 0;
                }

                private boolean strEquals(String x, String y) {
                    if (x == null && y == null) return true;
                    if (x == null || y == null) return false;
                    return x.equals(y);
                }
            };

    public PizzaAdapter() { super(DIFF_CALLBACK); }

    @NonNull
    @Override
    public PizzaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pizza, parent, false);
        return new PizzaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PizzaViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    static class PizzaViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgPizza;
        private final TextView  tvNombre;
        private final TextView  tvPrecio;

        PizzaViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPizza = itemView.findViewById(R.id.imgPizza);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
        }

        void bind(ProductoDto pizza) {
            tvNombre.setText(pizza.getNombre() != null ? pizza.getNombre() : "");

            double precio = pizza.getPrecio();
            tvPrecio.setVisibility(precio > 0 ? View.VISIBLE : View.GONE);
            if (precio > 0) tvPrecio.setText(String.format("$%.0f", precio));

            String ruta = pizza.getRuta();
            if (ruta != null && !ruta.isEmpty()) {
                String imgUrl = ruta.startsWith("http") ? ruta
                        : BASE_IMG_URL + (ruta.startsWith("/") ? ruta : "/" + ruta);
                Glide.with(itemView.getContext())
                        .load(imgUrl)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .placeholder(android.R.drawable.ic_menu_gallery)
                        .error(android.R.drawable.ic_menu_gallery)
                        .into(imgPizza);
            } else {
                imgPizza.setImageResource(android.R.drawable.ic_menu_gallery);
            }

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), PizzaDetalleActivity.class);
                intent.putExtra("id",           pizza.getId());
                intent.putExtra("nombre",       pizza.getNombre());
                intent.putExtra("descripcion",  pizza.getDescripcion());
                intent.putExtra("ingredientes", pizza.getIngredientes());
                intent.putExtra("ruta",         pizza.getRuta());
                v.getContext().startActivity(intent);
            });
        }
    }
}

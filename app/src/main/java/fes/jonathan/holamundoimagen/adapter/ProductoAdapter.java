package fes.jonathan.holamundoimagen.adapter;

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

import fes.jonathan.holamundoimagen.R;
import fes.jonathan.holamundoimagen.models.ProductoDto;

public class ProductoAdapter extends ListAdapter<ProductoDto, ProductoAdapter.ViewHolder> {

    private static final String BASE_URL = "https://utilidades.vmartinez84.xyz";

    public interface OnClickListener {
        void onClick(ProductoDto producto);
    }

    private final OnClickListener listener;

    public ProductoAdapter(OnClickListener listener) {
        super(DIFF);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_producto, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int pos) {
        ProductoDto p = getItem(pos);

        h.tvNombre.setText(p.getNombre() != null ? p.getNombre() : "");

        if (p.getPrecio() > 0) {
            h.tvPrecio.setVisibility(View.VISIBLE);
            h.tvPrecio.setText(String.format("$%.2f", p.getPrecio()));
        } else {
            h.tvPrecio.setVisibility(View.GONE);
        }

        String ruta = p.getRuta();
        if (ruta != null && !ruta.isEmpty()) {
            String imageUrl = ruta.startsWith("http") ? ruta
                    : BASE_URL + (ruta.startsWith("/") ? ruta : "/" + ruta);
            Glide.with(h.itemView.getContext())
                    .load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .error(android.R.drawable.ic_menu_gallery)
                    .centerCrop()
                    .into(h.ivImagen);
        } else {
            h.ivImagen.setImageResource(android.R.drawable.ic_menu_gallery);
        }

        h.itemView.setOnClickListener(v -> listener.onClick(p));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImagen;
        TextView  tvNombre;
        TextView  tvPrecio;

        ViewHolder(@NonNull View v) {
            super(v);
            ivImagen = v.findViewById(R.id.ivImagen);
            tvNombre = v.findViewById(R.id.tvNombre);
            tvPrecio = v.findViewById(R.id.tvPrecio);
        }
    }

    private static final DiffUtil.ItemCallback<ProductoDto> DIFF =
            new DiffUtil.ItemCallback<ProductoDto>() {
                @Override
                public boolean areItemsTheSame(@NonNull ProductoDto a, @NonNull ProductoDto b) {
                    return a.getId() == b.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull ProductoDto a, @NonNull ProductoDto b) {
                    return a.getId() == b.getId()
                            && strEquals(a.getNombre(), b.getNombre())
                            && a.getPrecio() == b.getPrecio();
                }

                private boolean strEquals(String a, String b) {
                    if (a == null && b == null) return true;
                    if (a == null || b == null) return false;
                    return a.equals(b);
                }
            };
}

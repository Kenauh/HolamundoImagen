package fes.jonathan.holamundoimagen.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import fes.jonathan.holamundoimagen.R;
import fes.jonathan.holamundoimagen.carrito.CarritoItem;

/**
 * Adapter para el RecyclerView del carrito (CarritoActivity).
 *
 * Cada ítem muestra:
 *   - Etiqueta del ítem (ej. "Pepperoni · Grande · Delgada")
 *   - Subtotal formateado
 *   - Botón eliminar (🗑) que dispara OnDeleteListener
 *
 * La eliminación se delega al Activity vía callback para que el
 * CarritoManager y la UI se mantengan sincronizados.
 */
public class CarritoAdapter extends ListAdapter<CarritoItem, CarritoAdapter.CarritoViewHolder> {

    /** Callback que recibe la posición del ítem a eliminar. */
    public interface OnDeleteListener {
        void onDelete(int posicion);
    }

    private final OnDeleteListener deleteListener;

    // ── DiffUtil ──────────────────────────────────────────────────────────────
    private static final DiffUtil.ItemCallback<CarritoItem> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<CarritoItem>() {
                @Override
                public boolean areItemsTheSame(@NonNull CarritoItem a, @NonNull CarritoItem b) {
                    // Identidad por referencia (cada CarritoItem es único)
                    return a == b;
                }

                @Override
                public boolean areContentsTheSame(@NonNull CarritoItem a, @NonNull CarritoItem b) {
                    return a.getEtiqueta().equals(b.getEtiqueta())
                        && Double.compare(a.getSubtotal(), b.getSubtotal()) == 0;
                }
            };

    public CarritoAdapter(OnDeleteListener deleteListener) {
        super(DIFF_CALLBACK);
        this.deleteListener = deleteListener;
    }

    // ── Ciclo de vida ─────────────────────────────────────────────────────────

    @NonNull
    @Override
    public CarritoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_carrito, parent, false);
        return new CarritoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarritoViewHolder holder, int position) {
        holder.bind(getItem(position), position, deleteListener);
    }

    // ── ViewHolder ────────────────────────────────────────────────────────────

    static class CarritoViewHolder extends RecyclerView.ViewHolder {

        private final TextView    tvEtiqueta;
        private final TextView    tvSubtotal;
        private final ImageButton btnEliminar;

        CarritoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEtiqueta  = itemView.findViewById(R.id.tvItemEtiqueta);
            tvSubtotal  = itemView.findViewById(R.id.tvItemSubtotal);
            btnEliminar = itemView.findViewById(R.id.btnEliminarItem);
        }

        void bind(CarritoItem item, int posicion, OnDeleteListener listener) {
            tvEtiqueta.setText(item.getEtiqueta());

            double sub = item.getSubtotal();
            tvSubtotal.setText(sub > 0 ? String.format("$%.0f", sub) : "");

            btnEliminar.setOnClickListener(v -> {
                if (listener != null) listener.onDelete(posicion);
            });
        }
    }
}

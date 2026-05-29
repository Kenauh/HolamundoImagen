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

public class CarritoAdapter extends ListAdapter<CarritoItem, CarritoAdapter.CarritoViewHolder> {

    public interface OnDeleteListener {
        void onDelete(int posicion);
    }

    private final OnDeleteListener deleteListener;

    private static final DiffUtil.ItemCallback<CarritoItem> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<CarritoItem>() {
                @Override
                public boolean areItemsTheSame(@NonNull CarritoItem a, @NonNull CarritoItem b) {
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

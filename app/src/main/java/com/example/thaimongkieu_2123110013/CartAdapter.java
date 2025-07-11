package com.example.thaimongkieu_2123110013;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private final List<Product> list;
    private OnCartChangedListener cartChangedListener;

    public interface OnCartChangedListener {
        void onCartUpdated(double total);
        void onItemRemoved(Product removedItem);
    }

    public void setOnCartChangedListener(OnCartChangedListener listener) {
        this.cartChangedListener = listener;
    }

    public CartAdapter(List<Product> list) {
        this.list = list;
    }

    private String formatCurrency(double amount) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return formatter.format(amount);
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product p = list.get(position);

        holder.name.setText(p.getName());
        holder.price.setText("Giá: " + formatCurrency(p.getDiscountPrice()));
        holder.quantity.setText(String.valueOf(p.getQuantity()));

        Glide.with(holder.itemView.getContext())
                .load(p.getImageUrl())
                .placeholder(R.drawable.p1)
                .into(holder.image);

        // Tăng số lượng
        holder.btnIncrease.setOnClickListener(v -> {
            p.setQuantity(p.getQuantity() + 1);
            holder.quantity.setText(String.valueOf(p.getQuantity()));
            notifyTotalChanged();
        });

        // Giảm số lượng
        holder.btnDecrease.setOnClickListener(v -> {
            if (p.getQuantity() > 1) {
                p.setQuantity(p.getQuantity() - 1);
                holder.quantity.setText(String.valueOf(p.getQuantity()));
                notifyTotalChanged();
            }
        });

        // Xoá sản phẩm
        holder.btnDelete.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            Product removed = list.remove(pos);
            notifyItemRemoved(pos);
            notifyTotalChanged();
            if (cartChangedListener != null) {
                cartChangedListener.onItemRemoved(removed);
            }
            Toast.makeText(holder.itemView.getContext(), "Đã xoá " + p.getName(), Toast.LENGTH_SHORT).show();
        });

        // Checkbox chọn sản phẩm
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            p.setSelected(isChecked);
            notifyTotalChanged();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void notifyTotalChanged() {
        if (cartChangedListener != null) {
            double total = 0;
            for (Product p : list) {
                if (p.isSelected()) {
                    total += p.getQuantity() * p.getDiscountPrice();
                }
            }
            cartChangedListener.onCartUpdated(total);
        }
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, price, quantity;
        ImageButton btnIncrease, btnDecrease, btnDelete;
        CheckBox checkBox;

        public CartViewHolder(@NonNull View v) {
            super(v);
            image = v.findViewById(R.id.imageProduct);
            name = v.findViewById(R.id.textProductName);
            price = v.findViewById(R.id.textPrice);
            quantity = v.findViewById(R.id.textQuantity);
            btnIncrease = v.findViewById(R.id.btnIncrease);
            btnDecrease = v.findViewById(R.id.btnDecrease);
            btnDelete = v.findViewById(R.id.btnDelete);
            checkBox = v.findViewById(R.id.checkboxSelect);
        }
    }
}

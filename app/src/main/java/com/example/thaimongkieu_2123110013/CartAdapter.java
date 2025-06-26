package com.example.thaimongkieu_2123110013;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Product> productList;
    private SparseBooleanArray selectedStates = new SparseBooleanArray();
    private HashMap<Integer, Integer> quantityMap = new HashMap<>();

    private OnItemCheckedChangeListener listener;

    public interface OnItemCheckedChangeListener {
        void onItemCheckedChanged(int totalSelectedPrice);
    }

    public void setOnItemCheckedChangeListener(OnItemCheckedChangeListener listener) {
        this.listener = listener;
    }

    public CartAdapter(List<Product> productList) {
        this.productList = productList;
        // mặc định mỗi sản phẩm có số lượng = 1
        for (int i = 0; i < productList.size(); i++) {
            quantityMap.put(i, 1);
        }
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.name.setText(product.name);
        holder.price.setText(product.newPrice);
        holder.image.setImageResource(product.imageResId);

        // Lấy số lượng hiện tại từ map
        int quantity = quantityMap.get(position);
        holder.quantity.setText(String.valueOf(quantity));

        // Checkbox
        holder.checkbox.setOnCheckedChangeListener(null);
        holder.checkbox.setChecked(selectedStates.get(position, false));
        holder.checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            selectedStates.put(position, isChecked);
            if (listener != null) {
                listener.onItemCheckedChanged(getTotalSelectedPrice());
            }
        });

        // Tăng số lượng
        holder.btnIncrease.setOnClickListener(v -> {
            int current = quantityMap.get(position);
            quantityMap.put(position, current + 1);
            holder.quantity.setText(String.valueOf(current + 1));
            if (listener != null) {
                listener.onItemCheckedChanged(getTotalSelectedPrice());
            }
        });

        // Giảm số lượng
        holder.btnDecrease.setOnClickListener(v -> {
            int current = quantityMap.get(position);
            if (current > 1) {
                quantityMap.put(position, current - 1);
                holder.quantity.setText(String.valueOf(current - 1));
                if (listener != null) {
                    listener.onItemCheckedChanged(getTotalSelectedPrice());
                }
            }
        });
    }

    private int getTotalSelectedPrice() {
        int total = 0;
        for (int i = 0; i < productList.size(); i++) {
            if (selectedStates.get(i, false)) {
                String raw = productList.get(i).newPrice.replaceAll("[^0-9]", "");
                int price = raw.isEmpty() ? 0 : Integer.parseInt(raw);
                int quantity = quantityMap.get(i);
                total += price * quantity;
            }
        }
        return total;
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkbox;
        ImageView image;
        TextView name, price, quantity;
        Button btnIncrease, btnDecrease;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            checkbox = itemView.findViewById(R.id.checkboxSelect);
            image = itemView.findViewById(R.id.imageProduct);
            name = itemView.findViewById(R.id.textProductName);
            price = itemView.findViewById(R.id.textPrice);
            quantity = itemView.findViewById(R.id.textQuantity);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
        }
    }
}

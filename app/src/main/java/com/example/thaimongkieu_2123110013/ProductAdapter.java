package com.example.thaimongkieu_2123110013;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    public interface OnCartClickListener {
        void onAddToCartClicked(Product product);
    }

    private final Context context;
    private final List<Product> list;
    private final OnCartClickListener cartClickListener;

    public ProductAdapter(Context context, List<Product> list, OnCartClickListener cartClickListener) {
        this.context = context;
        this.list = list;
        this.cartClickListener = cartClickListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder h, int position) {
        Product p = list.get(position);

        Glide.with(context)
                .load(p.getImageUrl())
                .into(h.img);

        h.name.setText(p.getName());
        h.oldPrice.setText(String.format("Giá gốc: %.0f đ", p.getOriginalPrice()));
        h.newPrice.setText(String.format("Giá giảm: %.0f đ", p.getDiscountPrice()));
        h.description.setText(p.getDescription());

        h.btnView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("product", p);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });

        h.btnCart.setOnClickListener(v -> {
            CartManager.addProduct(p); // Thêm sản phẩm vào giỏ hàng
            Toast.makeText(context, "Đã thêm: " + p.getName(), Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name, oldPrice, newPrice, description;
        Button btnView, btnCart;

        public ProductViewHolder(@NonNull View v) {
            super(v);
            img = v.findViewById(R.id.imgProduct);
            name = v.findViewById(R.id.tvProductName);
            oldPrice = v.findViewById(R.id.tvPriceOld);
            newPrice = v.findViewById(R.id.tvPriceNew);
            description = v.findViewById(R.id.tvDescription);
            btnView = v.findViewById(R.id.btnViewDetail);
            btnCart = v.findViewById(R.id.btnAddToCart);
        }
    }
}

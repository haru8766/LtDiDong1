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

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private final Context context;
    private final List<Product> list;

    public ProductAdapter(Context context, List<Product> list) {
        this.context = context;
        this.list = list;
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
        h.img.setImageResource(p.imageResId);
        h.name.setText(p.name);
        h.oldPrice.setText(p.oldPrice);
        h.newPrice.setText(p.newPrice);
        h.description.setText(p.description);

        h.btnView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("name", p.name);
            intent.putExtra("img", p.imageResId);
            intent.putExtra("oldPrice", p.oldPrice);
            intent.putExtra("newPrice", p.newPrice);
            intent.putExtra("description", p.description);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });

        h.btnCart.setOnClickListener(v ->
                Toast.makeText(context, "Đã thêm: " + p.name, Toast.LENGTH_SHORT).show()
        );
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


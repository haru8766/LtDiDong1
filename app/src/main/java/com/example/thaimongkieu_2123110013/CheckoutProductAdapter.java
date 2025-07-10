package com.example.thaimongkieu_2123110013;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//public class CheckoutProductAdapter extends RecyclerView.Adapter<CheckoutProductAdapter.ViewHolder> {
//
//    private final Context context;
//    private final List<Product> productList;
//
//    public CheckoutProductAdapter(Context context, List<Product> productList) {
//        this.context = context;
//        this.productList = productList;
//    }
//
//    @NonNull
//    @Override
//    public CheckoutProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.item_checkout_product, parent, false);
//        return new ViewHolder(view);
//    }
//
////    @Override
////    public void onBindViewHolder(@NonNull CheckoutProductAdapter.ViewHolder holder, int position) {
////        Product p = productList.get(position);
////        holder.img.setImageResource(p.imageResId);
////        holder.name.setText(p.name);
////        holder.price.setText(p.newPrice);
////    }
//
//    @Override
//    public int getItemCount() {
//        return productList.size();
//    }
//
//    static class ViewHolder extends RecyclerView.ViewHolder {
//        ImageView img;
//        TextView name, price;
//
//        public ViewHolder(@NonNull View v) {
//            super(v);
//            img = v.findViewById(R.id.imgCheckoutProduct);
//            name = v.findViewById(R.id.tvCheckoutName);
//            price = v.findViewById(R.id.tvCheckoutPrice);
//        }
//    }
//}

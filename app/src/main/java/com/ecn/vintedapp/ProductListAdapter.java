package com.ecn.vintedapp;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class ProductListAdapter extends ListAdapter<Product, ProductViewHolder>  {

    private OnProductClickListener onProductClickListener;


    public ProductListAdapter(@NonNull DiffUtil.ItemCallback<Product> diffCallback) {
        super(diffCallback);
    }

    public ProductListAdapter(@NonNull DiffUtil.ItemCallback<Product> diffCallback,OnProductClickListener listerner) {
        super(diffCallback);
        this.onProductClickListener=listerner;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ProductViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Log.d("","onBindViewHolder");

        Product current = getItem(position);
        holder.bind(current);
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the button click event
                onProductClickListener.onProductClick(current);
            }
        });
    }

    static class ProductDiff extends DiffUtil.ItemCallback<Product> {

        @Override
        public boolean areItemsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem.getId()==newItem.getId();
        }
    }
    public interface OnProductClickListener {
        void onProductClick(Product product);
    }
}



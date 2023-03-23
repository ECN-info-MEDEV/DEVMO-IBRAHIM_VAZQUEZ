package com.ecn.vintedapp;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ProductViewHolder  extends RecyclerView.ViewHolder {

    private final TextView productDescriptionView;

    private final TextView productPriceView;

    private final TextView productNameView;

    private final ImageView photo;

    private final TextView productCategoryView;

    public final Button editButton;


    private ProductViewHolder(View itemView) {

        super(itemView);
        Log.d("Product","Creating view Holder");

        productDescriptionView=itemView.findViewById(R.id.descriptionView);
        productPriceView=itemView.findViewById(R.id.priceView);
        productNameView=itemView.findViewById(R.id.nameView);
        photo=itemView.findViewById(R.id.imgView);
        editButton=itemView.findViewById(R.id.buttonEditingProduct);

        photo.setImageResource(R.drawable.shirtpicture);

        productCategoryView = itemView.findViewById(R.id.categoryView);
        /**
        editButton.setOnClickListener(view -> {
            Intent intent = new Intent(ProductListActivity.class, NewProductActivity.class);
            intent.putExtra("productId", product.getId());
            intent.putExtra("productName", product.getName());
            intent.putExtra("productPrice", product.getPrice());
            startActivity(intent);
        });**/


    }

    public void bind(Product product) {
        Log.d("Product","Trying to bind");
        productDescriptionView.setText(product.getDescription());
        productPriceView.setText(product.getPrice());
        productNameView.setText(product.getName());
        String fullCategory="Category : "+product.getCategory();
        productCategoryView.setText(fullCategory);
        String fullDescription="Description : " +product.getDescription();
        String fullPrice="Price : "+product.getPrice()+" €";
        String fullName="Name : "+product.getName();
        productDescriptionView.setText(fullDescription);
        productPriceView.setText(fullPrice);
        productNameView.setText(fullName);




    }

    static ProductViewHolder create(ViewGroup parent) {
        Log.d("","ProductViewHolder trying to create");

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new ProductViewHolder(view);
    }
}

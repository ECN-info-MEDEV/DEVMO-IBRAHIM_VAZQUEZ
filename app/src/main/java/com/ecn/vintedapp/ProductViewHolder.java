package com.ecn.vintedapp;

import android.graphics.drawable.Drawable;
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

    private final Button editButton;


    private ProductViewHolder(View itemView) {

        super(itemView);
        Log.d("Product","Creating view Holder");

        productDescriptionView=itemView.findViewById(R.id.descriptionView);
        productPriceView=itemView.findViewById(R.id.priceView);
        productNameView=itemView.findViewById(R.id.nameView);
        photo=itemView.findViewById(R.id.imgView);
        editButton=itemView.findViewById(R.id.buttonEditingProduct);

        photo.setImageResource(R.drawable.shirtpicture);



    }

    public void bind(Product product) {
        Log.d("Product","Trying to bind");
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

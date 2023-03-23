package com.ecn.vintedapp;

import static com.ecn.vintedapp.MainActivity.NEW_WORD_ACTIVITY_REQUEST_CODE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProductListActivity extends AppCompatActivity implements ProductListAdapter.OnProductClickListener{

    private ProductViewModel mProductViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        Log.d("Main Activity", "Creating new ProductModelView");

        mProductViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        final ProductListAdapter adapter = new ProductListAdapter(new ProductListAdapter.ProductDiff(),this);
        Log.d("Main Activity", "Creating new ProductListAdapter");

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get a new or existing ViewModel from the ViewModelProvider.

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        mProductViewModel.getAllProducts().observe(this, products -> {
            Log.d("","Mainactivity : trying to submitList");

            // Update the cached copy of the words in the adapter.
            adapter.submitList(products);
        });

        Button editItem=recyclerView.findViewById(R.id.buttonEditingProduct);
        if(editItem!=null){
            editItem.setOnClickListener(view->{
                Intent intent = new Intent(ProductListActivity.this, NewProductActivity.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
                intent.putExtra("name","test");


            });

        }


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(ProductListActivity.this, NewProductActivity.class);
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("ProductListActivity","Receiving the object ");

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Log.d("ProductListActivity","Receiving the object : the response is ok ");

            Product product = new Product(data.getStringExtra(NewProductActivity.EXTRA_REPLY));
            String priceResponse=data.getStringExtra("price");
            String name=data.getStringExtra("name");
            String finalDescription= data.getStringExtra("description");
            product.setDescription(finalDescription);
            product.setName(name);
            product.setPrice(priceResponse);


            mProductViewModel.insert(product);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    public void onProductClick(Product product) {
        // Handle the product click event
        Intent intent = new Intent(this, NewProductActivity.class);
        intent.putExtra("productId", product.getId());
        intent.putExtra("productName", product.getName());
        intent.putExtra("productPrice", product.getPrice());
        intent.putExtra("description",product.getDescription());
        startActivity(intent);
    }
}
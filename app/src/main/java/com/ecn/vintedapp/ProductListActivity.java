package com.ecn.vintedapp;

import static com.ecn.vintedapp.MainActivity.NEW_WORD_ACTIVITY_REQUEST_CODE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProductListActivity extends AppCompatActivity {

    private ProductViewModel mProductViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        Log.d("Main Activity", "Creating new ProductModelView");

        mProductViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        final ProductListAdapter adapter = new ProductListAdapter(new ProductListAdapter.ProductDiff());
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

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(ProductListActivity.this, NewProductActivity.class);
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        });
    }
}
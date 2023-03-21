package com.ecn.vintedapp;

/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivityCopy extends AppCompatActivity {

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;


    private ProductViewModel mProductViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Main Activity", "launching onCreate method of main");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            Log.d("","Mainactivity : tryoing to submitList");

            // Update the cached copy of the words in the adapter.
            adapter.submitList(products);
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivityCopy.this, NewProductActivity.class);
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("MainActivity","Recieving the object ");

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Log.d("MainActivity","Recieving the object : the response is ok ");

            Product product = new Product(data.getStringExtra(NewProductActivity.EXTRA_REPLY));
            String priceResponse=data.getStringExtra("price");
            String finalPrice="Price : "+priceResponse;
            String name=data.getStringExtra("name");
            String finalName="Name : "+name;
            String finalDescription="Description : " + data.getStringExtra("description");
            product.setDescription(finalDescription);
            product.setName(finalName);
            product.setPrice(finalPrice);


            mProductViewModel.insert(product);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
}
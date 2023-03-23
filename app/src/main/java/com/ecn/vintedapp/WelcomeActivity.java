package com.ecn.vintedapp;

import static com.ecn.vintedapp.MainActivity.NEW_WORD_ACTIVITY_REQUEST_CODE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity {

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;


    private ProductViewModel mProductViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("WelcomeActivity","Creating the activity");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Button buttonAddItem=findViewById(R.id.buttonAddItem);

        buttonAddItem.setOnClickListener(view->{
            Intent intent = new Intent(WelcomeActivity.this, NewProductActivity.class);
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);


        });

        Button yourStoreButton=findViewById(R.id.buttonYourStore);

        buttonAddItem.setOnClickListener(view->{

        });


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("MainActivity","Receiving the object ");

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Log.d("MainActivity","Receiving the object : the response is ok ");

            Product product = new Product(data.getStringExtra(NewProductActivity.EXTRA_REPLY));
            String priceResponse=data.getStringExtra("price");
            int id=data.getIntExtra("id",-1);
            String name=data.getStringExtra("name");
            product.setDescription(data.getStringExtra("description"));
            product.setName(name);
            product.setPrice(priceResponse);
            product.setCategory(data.getStringExtra("category"));
            if( id>0){
                Log.d("ProductListActivity","Receiving the object : the response is ok ");

                mProductViewModel.update(product);
            }

            mProductViewModel.insert(product);
        } else {
            Log.d("MainActivity","Problem while recieving the response object ");

            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
}
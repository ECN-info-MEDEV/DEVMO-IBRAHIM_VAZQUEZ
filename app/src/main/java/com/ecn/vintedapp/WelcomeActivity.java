package com.ecn.vintedapp;

import static com.ecn.vintedapp.MainActivity.NEW_WORD_ACTIVITY_REQUEST_CODE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
}
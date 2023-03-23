package com.ecn.vintedapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NewProductActivity extends AppCompatActivity {

    private final int GALLERY_REQ_CODE=1000;

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";

    private EditText mEditDescriptionView;

    private EditText mEditPriceView;

    private EditText mEditNameView;

    private ImageView imgGallery;

    private Spinner mEditCategoryView;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("","Creating new product activity");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);
        mEditDescriptionView = findViewById(R.id.edit_description);
        mEditPriceView=findViewById(R.id.edit_price);
        mEditNameView=findViewById(R.id.edit_name);
        mEditCategoryView=findViewById(R.id.edit_Category);
        final Button buttonImage=findViewById(R.id.imageButton);
        imgGallery=findViewById(R.id.edit_image);
        buttonImage.setOnClickListener(view->{
            Intent idGallery=new Intent(Intent.ACTION_PICK);
            idGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(idGallery,GALLERY_REQ_CODE);

        });

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(mEditDescriptionView.getText()) || TextUtils.isEmpty(mEditPriceView.getText())
                    || imgGallery.getDrawable()==null||TextUtils.isEmpty(mEditNameView.getText())
                    || TextUtils.isEmpty(mEditCategoryView.getSelectedItem().toString())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                Log.d("","trying to add new product");
                String description = mEditDescriptionView.getText().toString();
                String price =mEditPriceView.getText().toString();
                String name=mEditNameView.getText().toString();
                String category=mEditCategoryView.getSelectedItem().toString();
                replyIntent.putExtra("price",price);
                replyIntent.putExtra("name",name);
                replyIntent.putExtra("category",category);

                Drawable content=imgGallery.getDrawable();
                //replyIntent.putExtra("photo",content);

                replyIntent.putExtra("description",description);
                Log.d("","end trying to add new product");

                setResult(RESULT_OK, replyIntent);
            }
            Log.d("","calling finishmethod");

            finish();
            Log.d("","end calling finishmethod");

        });

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            int productId = extras.getInt("productId");
            Log.d("NewProductActivity ",String.format("the product id is %s",productId));
            String productName = extras.getString("productName");
            String productPrice = extras.getString("productPrice");
            String description=extras.getString("description");
            mEditNameView.setText(productName);
            mEditPriceView.setText(productPrice);
            mEditDescriptionView.setText(description);

            // Do something with the product information
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        Log.d("NewProductActivity","calling onActivityResult");

        if(resultCode==RESULT_OK){
            if(requestCode==GALLERY_REQ_CODE){
                imgGallery.setImageURI(data.getData());


            }
        }


    }
}

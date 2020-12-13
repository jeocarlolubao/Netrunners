package com.example.netrunners;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class AllProductsActivity_6 extends AppCompatActivity {

    ImageView previous;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products_6);

        previous = findViewById(R.id.imageView_previous);

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAllProductActivity_5();
            }
        });

    }

    public void openAllProductActivity_5() {
        Intent intent = new Intent(this, AllProductsActivity_5.class);
        startActivity(intent);
    }

}
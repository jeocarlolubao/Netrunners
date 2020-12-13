package com.example.netrunners;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class AllProductsActivity_2 extends AppCompatActivity {

    ImageView previous, next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products_2);

        previous = findViewById(R.id.imageView_previous);
        next = findViewById(R.id.imageView_next);

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAllProductActivity();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAllProductActivity_3();
            }
        });

    }

    public void openAllProductActivity() {
        Intent intent = new Intent(this, AllProductsActivity.class);
        startActivity(intent);
    }

    public void openAllProductActivity_3() {
        Intent intent = new Intent(this, AllProductsActivity_3.class);
        startActivity(intent);
    }

}
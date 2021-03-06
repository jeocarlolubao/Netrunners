package com.example.netrunners;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyCartActivity extends AppCompatActivity {

    ImageView back;
    TextView back_2, button_checkOut;
    static TextView textView_total;
    RecyclerView recyclerView;
    CartAdapter cartAdapter;
    static ArrayList<MyProduct> order = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        back = findViewById(R.id.imageView_back);
        back_2 = findViewById(R.id.textView_back);
        recyclerView = findViewById(R.id.recyclerView);
        textView_total = findViewById(R.id.textView_total);
        button_checkOut = findViewById(R.id.button_checkOut);

        getTotal(this);

        cartAdapter = new CartAdapter(this, MyData.getAllCartProduct(this));
        cartAdapter.setHasStableIds(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cartAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHomeScreenActivity();
            }
        });

        back_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHomeScreenActivity();
            }
        });

        button_checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getDoubleTotal(getBaseContext()) <= 0) {
                    new AlertDialog.Builder(MyCartActivity.this)
                            .setTitle("Empty Shopping Cart")
                            .setMessage("Your Shopping Cart is empty, please add items to your shopping cart")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .show()
                            .setCancelable(false);
                } else {
                    ArrayList<MyProduct> temp = MyData.getAllCartProduct(getBaseContext());
                    for(int j = 0; j < temp.size(); j++) {
                        if(temp.get(j).getCheckbox() == 1) {
                            if(order.equals(null)) {
                                order.clear();
                            }
                            order.add(new MyProduct(temp.get(j).getCart_Id(),
                                    temp.get(j).getId(),
                                    temp.get(j).getImage(),
                                    temp.get(j).getName(),
                                    temp.get(j).getCategory(),
                                    temp.get(j).getPrice(),
                                    temp.get(j).getQuantity(),
                                    temp.get(j).getCheckbox()
                            ));
                        }
                    }
                    openOrderSummaryActivity();
                }
            }
        });

    }

    public void openOrderSummaryActivity() {
        Intent intent = new Intent(this, OrderSummaryActivity.class);
        intent.putExtra("total", getDoubleTotal(getBaseContext()));
        startActivity(intent);
    }


    public void openHomeScreenActivity() {
        Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);
    }

    public Double getDoubleTotal(Context context) {
        ArrayList<MyProduct> allCart = MyData.getAllCartProduct(context);
        Double total = 0.0;
        for (int j = 0; j < allCart.size(); j++) {
            if (allCart.get(j).getCheckbox() == 1) {
                total += allCart.get(j).getPrice() * allCart.get(j).getQuantity();
            }
        }
        return total;
    }

    // Sets the total of displayed on Cart Activity
    public static void getTotal(Context context) {
        ArrayList<MyProduct> allCart = MyData.getAllCartProduct(context);
        int total = 0;
        for (int j = 0; j < allCart.size(); j++) {
            if (allCart.get(j).getCheckbox() == 1) {
                total += allCart.get(j).getPrice() * allCart.get(j).getQuantity();
            }
        }
        textView_total.setText(MyData.FormatNumber(Double.valueOf(String.valueOf(total))));
    }

    ItemTouchHelper.SimpleCallback simpleCallback =
            new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    int position = viewHolder.getAdapterPosition();
                    ProductDatabase db = new ProductDatabase(getBaseContext());
                    db.deleteCartRow(String.valueOf(cartAdapter.cart.get(position).getCart_Id()));
                    cartAdapter.cart.remove(position);
                    cartAdapter.notifyItemRemoved(position);
                    getTotal(getBaseContext());
                }
            };

}
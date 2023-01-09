package com.apello.foodapp.Activity.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.apello.foodapp.Activity.Adapter.CartListAdapter;
import com.apello.foodapp.Activity.Helper.ManagementCart;
import com.apello.foodapp.Activity.Interface.ChangeNumberItemsListener;
import com.apello.foodapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CartListActivity extends AppCompatActivity {
     private RecyclerView.Adapter adapter;
     private RecyclerView recyclerViewList;
     private ManagementCart managementCart ;
     TextView totalFeeTxt, taxTxt, deliveryTxt, totalTxt, emptyTxt;
     private double tax;
     private ScrollView scrollview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);

        managementCart = new ManagementCart(this);
        initView();
        initList();

        CalculateCart();;
        bottomNavigation();
    }

    private void bottomNavigation(){
        FloatingActionButton floatingActionButton = findViewById(R.id.cartBtn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartListActivity.this, CartListActivity.class));
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartListActivity.this, MainActivity.class));
            }
        });
    }

    private void initView() {
        recyclerViewList = findViewById(R.id.recyclerViewCheckout);
        totalFeeTxt = findViewById(R.id.totalFee);
        taxTxt = findViewById(R.id.totalTax);
        deliveryTxt = findViewById(R.id.deliveryTotal);
        totalTxt = findViewById(R.id.totalFee);
        emptyTxt = findViewById(R.id.emptyTxt);
        scrollview = findViewById(R.id.scrollView3);
    }

    private void initList()
    {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        adapter = new CartListAdapter(managementCart.getListCart(), this, new ChangeNumberItemsListener(){
            @Override
            public void changed(){
                CalculateCart();
            }
        });

        recyclerViewList.setAdapter(adapter);
        if(managementCart.getListCart().isEmpty()){
            emptyTxt.setVisibility(View.VISIBLE);
            scrollview.setVisibility(View.GONE);
        }
        else
        {
            emptyTxt.setVisibility(View.GONE);
            scrollview.setVisibility(View.VISIBLE);
        }
    }

    public void CalculateCart(){
        double percentTax = 0.02;
        double delivery = 10;
        tax = Math.round((managementCart.getTotalFee()*percentTax)*100)/100;

        double total = (Math.round(managementCart.getTotalFee()+ tax+delivery) * 100) / 100;
        double itemTotal = Math.round(managementCart.getTotalFee()*100)/100;

        totalFeeTxt.setText("R" + String.valueOf(itemTotal));
        taxTxt.setText("R" + String.valueOf(tax));
        deliveryTxt.setText("R" + String.valueOf(delivery));
        totalTxt.setText("R" + String.valueOf(total));

    }
}
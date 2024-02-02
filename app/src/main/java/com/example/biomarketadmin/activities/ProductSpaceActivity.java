package com.example.biomarketadmin.activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.example.biomarketadmin.adapters.ProductAdapter;
import com.example.biomarketadmin.Product;
import com.example.biomarketadmin.R;
import com.example.biomarketadmin.firebase.ShowProducts;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ProductSpaceActivity extends AppCompatActivity {

    TextView topTitle;
    ImageView quit;

    FirebaseFirestore db;
    RecyclerView recyclerView;
    ArrayList<Product> productList;
    ProductAdapter my_Adapter;
    ShowProducts showProducts;
    int nbr = 0;

    @Override
    protected void onResume() {
        super.onResume();
        if(nbr==0){
            nbr = 1;
        }else {
            productList.clear();
            showProducts.getAll();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_space);
            db = FirebaseFirestore.getInstance();
            recyclerView = findViewById(R.id.recycleView);
//            recyclerView.setLayoutManager(new LinearLayoutManager(Product_Space.this));
            productList = new ArrayList<>();
            my_Adapter = new ProductAdapter(this, productList);
            recyclerView.setAdapter(my_Adapter);



            actionBar("Products Space");




            productList.clear();
            showProducts = new ShowProducts(this, productList, my_Adapter);
            showProducts.getAll();



    }


    public void actionBar(String title){
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setCustomView(R.layout.custom_actionbar);
        actionBar.setDisplayShowCustomEnabled(true);

        View customView = actionBar.getCustomView();
        topTitle = customView.findViewById(R.id.titleOfActionBar);
        quit = customView.findViewById(R.id.quit);
        quit.setVisibility(View.GONE);
        topTitle.setText(title);
    }






}
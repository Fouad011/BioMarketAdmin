package com.example.biomarketadmin.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.biomarketadmin.DeliveryModel;
import com.example.biomarketadmin.Product;
import com.example.biomarketadmin.ProductResume;
import com.example.biomarketadmin.R;
import com.example.biomarketadmin.adapters.DeliveryAdapter;
import com.example.biomarketadmin.adapters.LinkAdapter;
import com.example.biomarketadmin.adapters.OrderAdapter;
import com.example.biomarketadmin.firebase.DBDelivery;
import com.example.biomarketadmin.firebase.DBProductResume;

import java.util.ArrayList;
import java.util.List;

public class CommandDetailsActivity extends AppCompatActivity {

    TextView topTitle;
    ImageView quit;

    TextView priceSubTotal, priceTotal, livPrice;

    RecyclerView recyclerViewOrder, recyclerViewDelivery;
    List<Product> productModelList =  new ArrayList<>();
    List<DeliveryModel> deliveryModelList = new ArrayList<>();
    DeliveryAdapter deliveryAdapter;
    OrderAdapter orderAdapter;
    TextView userInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_command_details);

        actionBar("Commands Details");


        priceSubTotal = findViewById(R.id.priceSubTotal);
        priceTotal = findViewById(R.id.priceTotal);
        livPrice = findViewById(R.id.livraisonPrice);

        userInformation = findViewById(R.id.userInformations);

        recyclerViewOrder = findViewById(R.id.recycleViewProductsOrder);
        recyclerViewDelivery = findViewById(R.id.recyclerViewDelivery);

        productModelList = new ArrayList<>();
        orderAdapter = new OrderAdapter(getApplicationContext(), productModelList, priceSubTotal, priceTotal, 50.0);

        recyclerViewOrder.setAdapter(orderAdapter);



        Bundle extras = getIntent().getExtras();
//        ProductModel receivedObject = (ProductModel) getIntent().getSerializableExtra("object_key"); // For Serializable


        assert extras != null;
        System.out.println("ID:: " + extras.getString("commandId"));

        String commandId = (String) extras.get("commandId");
        String userId = (String) extras.get("userId");
        System.out.println("00++ userId " + userId);

        deliveryModelList = new ArrayList<>();
        deliveryAdapter = new DeliveryAdapter(getApplicationContext(), deliveryModelList, deliveryAdapter, userId, commandId);
        recyclerViewDelivery.setAdapter(deliveryAdapter);


        userInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent());
            }
        });

        DBDelivery dbDelivery = new DBDelivery(getApplicationContext(), deliveryModelList, deliveryAdapter);
        // get Delivery
        dbDelivery.getDelivery();


        DBProductResume dbProductResume = new DBProductResume();
//        set Information of Client
        dbProductResume.setInformations(getApplicationContext(), userId, userInformation);

//        set Information of Command
        dbProductResume.showCommandProducts(getApplicationContext(), userId, commandId, productModelList, orderAdapter);

        for (Product productModel : productModelList){
            System.out.println(productModel.getID() +" "+ productModel.getQuantity());
        }


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




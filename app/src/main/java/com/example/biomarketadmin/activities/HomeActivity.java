package com.example.biomarketadmin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


import com.example.biomarketadmin.R;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    TextView topTitle;
    ImageView quit;
//    ImageView qt;
    CardView customize, addProduct, command, addDelivery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        qt = findViewById(R.id.quit);
        customize = findViewById(R.id.custom);
        addProduct = findViewById(R.id.addp);
        command = findViewById(R.id.command);
        addDelivery = findViewById(R.id.adddeliv);




        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setCustomView(R.layout.custom_actionbar);
        actionBar.setDisplayShowCustomEnabled(true);


        View customView = actionBar.getCustomView();
        topTitle = customView.findViewById(R.id.titleOfActionBar);
        quit = customView.findViewById(R.id.quit);
        topTitle.setText("Home");



        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth auth;
                auth = FirebaseAuth.getInstance();
                auth.signOut();
                Intent i = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, AddProductActivity.class);
                startActivity(i);
            }
        });
        customize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, ProductSpaceActivity.class);
                startActivity(i);
            }
        });

        command.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), CommandsActivity.class)));

        addDelivery.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), AddDeliveryActivity.class)));
    }
}
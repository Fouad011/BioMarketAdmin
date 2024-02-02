package com.example.biomarketadmin.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.biomarketadmin.LinkModel;
import com.example.biomarketadmin.ProductResume;
import com.example.biomarketadmin.R;
import com.example.biomarketadmin.adapters.LinkAdapter;
import com.example.biomarketadmin.firebase.DBProductResume;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class CommandsActivity extends AppCompatActivity {

    TextView topTitle;
    ImageView quit;


    FirebaseFirestore db;

    RecyclerView recyclerViewLink;
    List<LinkModel> stringList;
    LinkAdapter linkAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commands);


        actionBar("Commands");


        db = FirebaseFirestore.getInstance();

        recyclerViewLink = findViewById(R.id.recycleViewLinks);

        stringList = new ArrayList<>();
        linkAdapter = new LinkAdapter(getApplicationContext(), stringList, linkAdapter);

        recyclerViewLink.setAdapter(linkAdapter);


        DBProductResume DBProductResume = new DBProductResume();
        DBProductResume.getCommandData(stringList, linkAdapter);

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
package com.example.biomarketadmin.firebase;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.biomarketadmin.DeliveryModel;
import com.example.biomarketadmin.Product;
import com.example.biomarketadmin.ProductResume;
import com.example.biomarketadmin.UserModel;
import com.example.biomarketadmin.activities.CommandsActivity;
import com.example.biomarketadmin.activities.HomeActivity;
import com.example.biomarketadmin.adapters.DeliveryAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DBDelivery {

    Context context;
    String prodId;
    DatabaseReference dbRef;
    List<DeliveryModel> deliveryModelList = new ArrayList<>();
    DeliveryAdapter deliveryAdapter;


    public DBDelivery() {
    }

    public DBDelivery(Context context, List<DeliveryModel> deliveryModelList, DeliveryAdapter deliveryAdapter) {
        this.context = context;
        this.deliveryModelList = deliveryModelList;
        this.deliveryAdapter = deliveryAdapter;
    }



    public void getDelivery() {

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("Deliverers");

        ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot snapshot = task.getResult();

                if(task.isSuccessful()){
                    for (DataSnapshot childSnapshot : snapshot.getChildren()){
                        DeliveryModel deliveryModel = childSnapshot.getValue(DeliveryModel.class);
                        assert deliveryModel != null;
                        deliveryModel.setId(childSnapshot.getKey());
                        System.out.println("--> " + deliveryModel.toString());
//                        deliveryModelList.add(deliveryModel);
                        deliveryAdapter.addProduct(deliveryModel);

                    }
//                    deliveryAdapter.notifyDataSetChanged();

                }else {
                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



    public void moveCommand(Context context1, String deliveryId, String userId, String commandId){


        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference refCommand = db.getReference("Commands");




        refCommand.child("Admin").child(userId).child(commandId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.isComplete()){
                        Object object = task.getResult().getValue(Object.class);
//                        assert productResume != null;
//                        productResume.setId(task.getResult().getKey());
//                        System.out.println("--> " + productResume.toString());
                        setMoveCommand(context1, object, deliveryId, userId, commandId);
                    }
                }else {
                    Toast.makeText(context1, "ERROR", Toast.LENGTH_SHORT).show();
                }
            }
        });





    }

    private void setMoveCommand(Context context1, Object object, String deliveryId,String userId, String commandId) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference refCommand = db.getReference("Commands");

        refCommand.child("Deliverers").child(deliveryId).child(userId).child(commandId).setValue(object).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isComplete()){
//                    Toast.makeText(context1, "Succ", Toast.LENGTH_SHORT).show();
//                    refClient.child(userId).child("Pannier").removeValue();
                    Toast.makeText(context1, "Sec to submit products", Toast.LENGTH_SHORT).show();
                    refCommand.child("Admin").child(userId).child(commandId).removeValue();

                    Intent intent = new Intent(context1, CommandsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context1.startActivity(intent);
                }else {
                    Toast.makeText(context1, "Field to submit products", Toast.LENGTH_SHORT).show();
                }
//                context1.startActivity(new Intent(context1, CommandsActivity.class));

            }
        });
    }

}

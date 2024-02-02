package com.example.biomarketadmin.firebase;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.example.biomarketadmin.LinkModel;
import com.example.biomarketadmin.Product;
import com.example.biomarketadmin.ProductResume;
import com.example.biomarketadmin.UserModel;
import com.example.biomarketadmin.adapters.LinkAdapter;
import com.example.biomarketadmin.adapters.OrderAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class DBProductResume {
    Context context;
    String prodId;
    int quantity;
    DatabaseReference dbRef;
    List<Product> productModelList = new ArrayList<>();



    public DBProductResume() {
    }

    public DBProductResume(Context context, String prodId, int quantity) {
        this.context = context;
        this.prodId = prodId;
        this.quantity = quantity;
    }





//    public void getCommandData(List<ProductResume> linkList, LinkAdapter linkAdapter3){
//
//
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("Commands");
//
//        linkAdapter3.clear();
//
//        myRef.child("Admin").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                System.out.println("-0->" + dataSnapshot.getValue());
//
//
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
//                    System.out.println("-1->" + dataSnapshot1.getKey());
//
//                    for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()){
//                        System.out.println("-2->" + dataSnapshot2.getKey());
//                        System.out.println("-3->" + dataSnapshot2.getValue());
//
//                        ProductResume productResume = new ProductResume();
//
//
//                        for (DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren()){
//                            productResume = dataSnapshot3.getValue(ProductResume.class);
//                            productResume.setCommandId(dataSnapshot2.getKey());
//                            productResume.setUserId(dataSnapshot1.getKey());
//
//
////                                linkList.add(productResume);
////                                linkAdapter.notifyDataSetChanged();
//
//                                linkAdapter3.addProduct(productResume);
//
//                        }
//
//                    }
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                // Handle errors
//                Log.w("TAG", "Failed to read value.", error.toException());
//            }
//        });
//
//    }



//    public void getCommandDataSecond(List<LinkModel> linkList, LinkAdapter linkAdapter){
//
//
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("Commands");
//
//
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        FirebaseUser user = auth.getCurrentUser();
//        assert user != null;
//        String userId = user.getUid();
//
//
//        myRef.addValueEventListener(new ValueEventListener() {
//            @SuppressLint("NotifyDataSetChanged")
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                // Get data from snapshot
////                Object value = dataSnapshot.getValue(Object.class);  // Or any specific data type
////                Log.d("TAG", "Value is: " + value);
//                System.out.println("-V->" + dataSnapshot.getValue());
//                linkList.clear();
//                linkAdapter.notifyDataSetChanged();
//
//
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
//                    System.out.println("-V1->" + dataSnapshot1.getKey());
//
//                    if(Objects.equals(dataSnapshot1.getKey(), "Admin")){
//                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()){
//                            System.out.println("\t-V2->" + dataSnapshot2.getKey());
//                            if(userId.equals(dataSnapshot2.getKey())){
//                                for (DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren()){
//                                    System.out.println("\t\t-V3->" + dataSnapshot3.getKey());
//                                    LinkModel linkModel = new LinkModel(dataSnapshot3.getKey());
//                                    linkModel.setValidity(false);
//                                    linkModel.setUserId(dataSnapshot2.getKey());
//                                    linkList.add(linkModel);
//                                    linkAdapter.notifyDataSetChanged();
//                                }
//                                break;
//                            }
//                        }
//                    } else if (Objects.equals(dataSnapshot1.getKey(), "Deliverers")) {
//                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()){
//                            System.out.println("\t-V2->" + dataSnapshot2.getKey());
//                            for (DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren()){
//                                System.out.println("\t\t-V3->" + dataSnapshot3.getKey());
//                                if(userId.equals(dataSnapshot3.getKey())){
//                                    for (DataSnapshot dataSnapshot4 : dataSnapshot3.getChildren()){
//                                        System.out.println("\t\t\t-V4->" + dataSnapshot4.getKey());
//                                        LinkModel linkModel = new LinkModel(dataSnapshot4.getKey());
//                                        linkModel.setDeliveryId(dataSnapshot2.getKey());
//                                        linkModel.setUserId(dataSnapshot3.getKey());
//                                        linkModel.setValidity(true);
//                                        linkList.add(linkModel);
//                                        linkAdapter.notifyDataSetChanged();
//                                    }
//                                    break;
//                                }
//                            }
//                        }
//                    }
//
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                // Handle errors
//                Log.w("TAG", "Failed to read value.", error.toException());
//            }
//        });
//
//    }


    public void getCommandData(List<LinkModel> linkList, LinkAdapter linkAdapter){


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Commands");




        myRef.child("Admin").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get data from snapshot
//                Object value = dataSnapshot.getValue(Object.class);  // Or any specific data type
//                Log.d("TAG", "Value is: " + value);
                System.out.println("-V->" + dataSnapshot.getValue());
                linkList.clear();
                linkAdapter.notifyDataSetChanged();


                for (DataSnapshot dataSnapshot0 : dataSnapshot.getChildren()){
                    System.out.println("-V0->" + dataSnapshot0.getKey());

                    for (DataSnapshot dataSnapshot1 : dataSnapshot0.getChildren()){
                        System.out.println("\t-V1->" + dataSnapshot1.getKey());

                        LinkModel linkModel = new LinkModel(dataSnapshot1.getKey());
                        linkModel.setUserId(dataSnapshot0.getKey());
                        linkList.add(linkModel);
                        linkAdapter.notifyDataSetChanged();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

    }



    public void showCommandProducts(Context context1, String userId,String commandId, List<Product> productModelList, OrderAdapter orderAdapter){

        context = context1;
        this.productModelList = productModelList;


        List<ProductResume> productResumeList = new ArrayList<>();





        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Commands");




        myRef
                .child("Admin")
                .child(userId)
                .child(commandId)
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                System.out.println(dataSnapshot);
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                    ProductResume productResume = dataSnapshot1.getValue(ProductResume.class);

//                    ProductModel productModel = new ProductModel();
//                    assert productResume != null;
//                    productModel.setID(productResume.getId());
//                    productModel.setQuantity(productResume.getQuantity());

                    productResumeList.add(productResume);
                }

                showProducts(productResumeList, orderAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });


    }




    public void showProducts(List<ProductResume> productResumeList, OrderAdapter orderAdapter){



        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Query query = db.collection("Products").document("bioMarketStore").collection("products");

        query
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            if(task.isComplete()){
                                for (QueryDocumentSnapshot document : task.getResult()){
                                    for (ProductResume productResume : productResumeList){
                                        if(document.getId().equals(productResume.getId())){

//                                            System.out.println("\n\n\n" + document.getData() + "\n\n\n");

                                            Product product = document.toObject(Product.class);
                                            product.setID(productResume.getId());
                                            product.setQuantity(productResume.getQuantity());

                                            orderAdapter.addProduct(product);
//                                            productModelList.add(product);
//                                            orderAdapter.notifyDataSetChanged();

//                                            System.out.print("--+ ");
//                                            System.out.println(product);

                                        }
                                    }

                                }
                            }
                        }else {
                            Toast.makeText(context, "ERROR, task is not successful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public Product addProductResumeToProductModel(Product productModel, ProductResume productResume){

        productModel.setID(productResume.getId());
        productModel.setQuantity(productModel.getQuantity());

        return productModel;
    }


    public void setInformations(Context applicationContext, String userId, TextView userInformationLine1) {

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("Clients");

        ref.child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    UserModel userModel = task.getResult().getValue(UserModel.class);
//                    userModel.setId(task.getResult().getKey());
//                    System.out.println(userModel.toString());
                    assert userModel != null;
                    String info2 = userModel.address.getCity() + ", " + userModel.address.getStreet() + ", " + userModel.address.getZipCode();
                    String info1 = userModel.getFullName() + " | " + userModel.getMobile() + "\n" + info2;
                    userInformationLine1.setText(info1);
                }else {
                    Toast.makeText(applicationContext, "ERROR", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}

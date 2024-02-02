package com.example.biomarketadmin.firebase;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.example.biomarketadmin.adapters.ProductAdapter;
import com.example.biomarketadmin.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ShowProducts {
    Context context;
    ArrayList<Product> productList;
    ProductAdapter productAdapter;
    FirebaseFirestore db;

    public ShowProducts(){}

    public ShowProducts(Context context, ArrayList<Product> productList, ProductAdapter productAdapter) {
        this.context = context;
        this.productList = productList;
        this.productAdapter = productAdapter;
        this.db = FirebaseFirestore.getInstance();
    }


    public void getAll(){
        Query query = this.db.collection("Products").document("bioMarketStore").collection("products");
        query
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                Product product = document.toObject(Product.class);
                                product.setID(document.getId());
                                productAdapter.addProduct(product);
//                                productList.add(product);
//                                productAdapter.notifyDataSetChanged();
                            }
                        }else {
                            Toast.makeText(context, "ERROR, task failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public void setData(String prodId, Product product, FirebaseFirestore baseStore, DocumentReference docRef){

//        docRef.update("name",nm, "category", ct, "classification", clf, "price", px, "description", dx, "imageUrl", imageUrl, "visible", show, "avaible", store)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        // Handle successful update
//                        Log.d(TAG, "Document updated successfully!");
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        // Handle unsuccessful update
//                        Log.w(TAG, "Error updating document", e);
//                    }
//                });
    }




}







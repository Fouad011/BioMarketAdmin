package com.example.biomarketadmin.activities;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SwitchCompat;

import com.bumptech.glide.Glide;
import com.example.biomarketadmin.Product;
import com.example.biomarketadmin.R;
import com.example.biomarketadmin.firebase.ShowProducts;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class EditProductActivity extends AppCompatActivity {
    TextView topTitle;
    ImageView quit;

    static final int PICK_IMAGE_REQUEST = 1;
    EditText nom, categorie, prix, description, classification, editTextQntStock;
    SwitchCompat inStore,inShow;    String productId;
    ImageView image; Uri uriPic; AppCompatButton Submit;
    FirebaseFirestore baseStore;
    DocumentReference docRef;

    String nameTmp, categoryTmp, describeTmp, classifyTmp, imgUrlTmp;
    Double priceTmp;
    int qntStockTmp;
    boolean avaibleTmp,visibleTmp;
    Map<String, Object> updates = new HashMap<>();

    Product product;



    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);


        nom = findViewById(R.id.name);
        categorie = findViewById(R.id.cat);
        prix = findViewById(R.id.prx);
        editTextQntStock = findViewById(R.id.qntStock);
        description = findViewById(R.id.dx);
        classification = findViewById(R.id.clsf);
        inStore = findViewById(R.id.ins);
        inShow = findViewById(R.id.vis);
        image = findViewById(R.id.picture);
        Submit = findViewById(R.id.btnSubmit);


        actionBar("Customize Product");


        Bundle extras = getIntent().getExtras();
        if (extras != null){

            product = (Product) extras.getSerializable("object");

            assert product != null;
            nom.setText(product.getTitle());
            categorie.setText(product.getCategory());
            prix.setText(Double.toString(product.getPrice()));
            editTextQntStock.setText(Integer.toString(product.getQuantityInStock()));
            description.setText(product.getDescription());
            classification.setText(product.getClassification());
            inStore.setChecked(product.getInStock());
            inShow.setChecked(product.getVisible());
            Glide.with(this).load(product.getImageUrl()).into(image);
            image.setVisibility(View.VISIBLE);

        }

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGal();
            }
        });



        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(getChangements() || uriPic!=null){
                    changeProductDetails();
                }else {
                    Toast.makeText(EditProductActivity.this, "pas des changements", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void changeProductDetails() {
//        System.out.println("uriPic" + uriPic.toString());

        if (uriPic!=null){
            StorageReference storageReference = FirebaseStorage.getInstance().getReference("Products");
            baseStore = FirebaseFirestore.getInstance();
            baseStore.collection("Products").document("bioMarketStore").collection("products");
            storageReference.child(UUID.randomUUID().toString()+"."+getFileExtension(uriPic)).putFile(uriPic).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String imageUrl = uri.toString();
                            baseStore.collection("Products").document("bioMarketStore").collection("products");
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            docRef = db.collection("Products").document("bioMarketStore").collection("products").document(product.getID());

//                        ShowProducts saveProduct = new ShowProducts();
//                        saveProduct.setData(productId, product, baseStore, docRef);
                            updates.put("imageUrl", imageUrl);

                            docRef.update(updates)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            // Handle successful update
                                            Toast.makeText(EditProductActivity.this, "Document updated successfully!", Toast.LENGTH_SHORT).show();
//                                            Log.d(TAG, "Document updated successfully!");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Handle unsuccessful update
                                            Toast.makeText(EditProductActivity.this, "Error updating document", Toast.LENGTH_SHORT).show();
//                                            Log.w(TAG, "Error updating document", e);
                                        }
                                    });
                        }
                    });



                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(EditProductActivity.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                }
            });


        }else {

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            docRef = db.collection("Products").document("bioMarketStore").collection("products").document(product.getID());


            docRef.update(updates)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Handle successful update
//                            Log.d(TAG, "Document updated successfully!");
                            Toast.makeText(EditProductActivity.this, "Document updated successfully!", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Handle unsuccessful update
//                            Log.w(TAG, "Error updating document", e);
                            Toast.makeText(EditProductActivity.this, "Error updating document", Toast.LENGTH_SHORT).show();

                        }
                    });
        }


    }

    public boolean getChangements(){

        boolean cond = false;

        String nm, ct,clf,px,dx, qt; boolean show,store;
        nm = nom.getText().toString(); ct = categorie.getText().toString(); clf = classification.getText().toString();
        dx = description.getText().toString(); show = inShow.isChecked();
        store = inStore.isChecked();
        if (editTextQntStock.getText().toString().isEmpty()) {
            qt = "0";
        }else {
            qt = editTextQntStock.getText().toString();
        }

        if (prix.getText().toString().isEmpty()) {
            px = "0";
        }else {
            px = prix.getText().toString();
        }

        if(!nm.equals(product.getTitle())){
            updates.put("title", nm);
            cond = true;
        }
        if(!ct.equals(product.getCategory())){
            updates.put("category", ct);
            cond = true;
        }
        if(!clf.equals(product.getClassification())){
            updates.put("classification", clf);
            cond = true;
        }
        if(!px.equals(product.getPrice().toString())){
            updates.put("price", Double.parseDouble(px));
            cond = true;
        }
        if(!qt.equals(Integer.toString(product.getQuantityInStock()))){
            updates.put("quantityInStock", Integer.parseInt(qt));
            cond = true;
        }
        if(!dx.equals(product.getDescription())){
            updates.put("description", dx);
            cond = true;
        }
        if(show!=product.getVisible()){
            updates.put("visible", show);
            cond = true;
        }
        if(store!=product.getInStock()){
            updates.put("inStock", store);
            cond = true;
        }

        return cond;

    }


    String getFileExtension(Uri uri) {
            ContentResolver cr = getContentResolver();
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    private void openGal() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i, PICK_IMAGE_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uriPic = data.getData();
            image.setImageURI(uriPic);
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